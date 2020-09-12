package mu.integration.producer.config;

import java.io.File;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.transformer.FileToStringTransformer;

@Configuration
public class ProducerConfig {
    public static final String INPUT_DIR = "D:\\test";
    public static final String FILE_EXTENSION = ".csv";
    public static final String LINE_DELIMITER = "\n";
    public static final String RETURN_DELIMITER = "\r";
    public static final String EMPTY = "";


    @Autowired
    private ConnectionFactory connectionFactory;

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
                .split(s -> s.applySequence(true).get().getT2().setDelimiters(LINE_DELIMITER))

                // cleanup lines from trailing returns
                .transform((String s) -> s.replace(LINE_DELIMITER, EMPTY).replace(RETURN_DELIMITER, EMPTY))

                // saves the csv lines
                .handle("csvLineServiceImpl", "saveCsvLine")

                //  sends it to RabbitMq message broker
                .handle("csvLineInformationSender", "send")

//                .handle(m -> System.out.println(m))

                .get();

    }


}

