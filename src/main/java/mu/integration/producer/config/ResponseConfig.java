package mu.integration.producer.config;

import java.io.File;

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.integration.file.support.FileExistsMode;

import mu.integration.producer.entity.CsvLineInformation;

@Configuration
public class ResponseConfig {


    @Bean
    public IntegrationFlow processReply() {

        return IntegrationFlows
                .from(Sink.INPUT)

                // saves the csv lines
                .handle("csvLineServiceImpl", "updateCsvLine")

////                .aggregate(m -> m.get)
                .aggregate(
                        a -> a.correlationStrategy(group ->
                                group.getPayload()

                                        .anyMatch(m ->
                                                FileSplitter.FileMarker.Mark.END.name()
                                                        .equals(m.getHeaders().get(FileHeaders.MARKER)))))


//
//                                a -> a.outputProcessor(g -> g.getMessages()
//                                        .stream()
//                                        .map(m -> ((CsvLineInformation) m.getPayload()).getFileName())
//                                        .collect(Collectors.joining(" "))))
//
////
                .handle(m -> System.out.println(m))
////
//                //write to csv file
//                .channel("csvFileWriter.input")
                .get();

    }


    @Bean
    public IntegrationFlow publishAggregator() {
        return f -> f.handle(m -> ((CsvLineInformation) m.getPayload()).getFileName());
    }

    @Bean
    public IntegrationFlow csvFileWriter() {
        return f -> f.handle(Files.outboundAdapter(new File("D:/test/output"))
                .appendNewLine(true)
                .fileExistsMode(FileExistsMode.APPEND)
                .fileNameGenerator(message -> "result.csv")
                .get());
    }
}

