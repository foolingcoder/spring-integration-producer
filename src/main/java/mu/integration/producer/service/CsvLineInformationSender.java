package mu.integration.producer.service;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CsvLineInformationSender {

    private final Source source;

    public void send(Message message) {
        log.debug("\n\n CsvLine sent: {}", message);
        this.source.output().send(message);
    }

}