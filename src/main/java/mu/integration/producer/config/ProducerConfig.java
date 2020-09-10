package mu.integration.producer.config;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class ProducerConfig {
    public static final String INPUT_DIR = "D:\\test";
    public static final String OUTPUT_DIR = "D:\\test\\output";
    public static final String FILE_EXTENSION = ".csv";
    public static final String LINE_DELIMETER = "\n";
    public static final String RETURN_DELIMITER = "\r";
    public static final String EMPTY = "";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Bean
    public MessageSource<File> CsvFileSource() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(INPUT_DIR));
        return messageSource;
    }

    @Bean
    public GenericSelector<File> onlyCsv() {
        return new GenericSelector<File>() {
            @Override
            public boolean accept(File source) {
                return source.getName().endsWith(FILE_EXTENSION);
            }
        };
    }

    @Bean
    public FileToStringTransformer convertToString() {
        FileToStringTransformer fileToStringTranformer = new FileToStringTransformer();
        // fileToStringTranformer.setDeleteFiles(true);
        return fileToStringTranformer;
    }

    @Bean
    public IntegrationFlow produce() {

        return IntegrationFlows
                .from(CsvFileSource(), configurer -> configurer.poller(Pollers.fixedDelay(10000)))

                //read only csv file
                .filter(onlyCsv())

                // get string contents from file
                .transform(convertToString())

                // split into individual lines
                .split(s -> s.applySequence(true).get().getT2().setDelimiters(LINE_DELIMETER))

                // cleanup lines from trailing returns
                .transform((String s) -> s.replace(LINE_DELIMETER, EMPTY).replace(RETURN_DELIMITER, EMPTY))

                // .handle("@csvLineService.saveCsv(payload)")
//                //write to file
//                .channel("process.input")

                //  sends it to RabbitMq and waits for the reply
                .handle(Amqp.outboundGateway(rabbitTemplateWithFixedReplyQueue()))

                //Reads the response from Rabbitmq
                .handle("replyMessageProcessor", "process")

                .aggregate()

                //convert to csv lines
                .transform((List<String> csvLineDtos) -> csvLineDtos.stream()
                        .collect(Collectors.joining(System.lineSeparator())))

                //write to csv file
                .channel("lines.input")

                .get();

    }

    @Bean
    public IntegrationFlow lines(FileWritingMessageHandler fileOut) {
        return f -> f.handle(fileOut);
    }

    @Bean
    public FileWritingMessageHandler fileOut() {
        return Files.outboundAdapter(new File("D:/test/output"))
                .appendNewLine(true)
                .fileExistsMode(FileExistsMode.APPEND)
                .fileNameGenerator(message -> "result.csv")
                .get();
    }


    /**
     * @return the preconfigured rabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplateWithFixedReplyQueue() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(queue().getName());
        rabbitTemplate.setReplyAddress(replyQueue().getName());
        return rabbitTemplate;

    }


    /**
     * @return the reply listener container - the rabbit template is the listener.
     */
    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(replyQueue());
        container.setMessageListener(rabbitTemplateWithFixedReplyQueue());
        return container;
    }


    @Bean
    public Queue queue() {
        return new Queue("myQueue", false);
    }


    @Bean
    public Queue replyQueue() {
        return new Queue("replyQueue", false);
    }

    @Bean
    public MessageChannel amqpOutboundChannel() {
        return new DirectChannel();
    }

}

