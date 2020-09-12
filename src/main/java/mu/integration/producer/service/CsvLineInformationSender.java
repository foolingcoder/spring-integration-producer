package mu.integration.producer.service;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.entity.CsvLineInformation;

/**
 * Builds a message and publishes it to the remote topic using {@link Source} bean
 *
 * @author Priteela
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CsvLineInformationSender {

    private final Source source;

    public void send(Message<CsvLineInformation> message) {
        log.debug("\n\n CsvLine sent: {}", message);
        this.source.output().send(message);
    }

}