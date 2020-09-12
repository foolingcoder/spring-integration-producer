package mu.integration.producer.config;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import mu.integration.producer.entity.CsvLineInformation;

@Configuration
public class ResponseConfig {
    public static final String OUTPUT_DIR = "D:\\test\\output";

    @Bean
    public IntegrationFlow processReply() {

        return IntegrationFlows
                //Read response from consumer
                .from(Sink.INPUT)

                // updates the csv lines with their statuses
                .handle("csvLineServiceImpl", "updateCsvLine")

                //aggregate the csv lines by file name
                .aggregate()

                //convert them into comma separated lines
                .transform((List<CsvLineInformation> csvLineFormations) -> csvLineFormations.stream()
                        .map(CsvLineInformation::toString)
                        .collect(Collectors.joining(System.lineSeparator())))

//                .handle(m -> System.out.println(m.getPayload()))

                //write to csv file
                .channel("csvFileWriter.input")

                .get();

    }


    @Bean
    public IntegrationFlow csvFileWriter() {
        return f -> f.handle(Files.outboundAdapter(new File(OUTPUT_DIR))
                .appendNewLine(true)
                .fileExistsMode(FileExistsMode.APPEND)
                .fileNameGenerator(message -> (String) message.getHeaders().get("file_relativePath"))
                .get());
    }
}

