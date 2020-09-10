package mu.integration.producer.handler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.entity.CsvLine;
import mu.integration.producer.service.CsvLineService;

/**
 *
 * @author priteela
 */
@Slf4j
@Component
public class LineMessageHandler {

    @Autowired
    private CsvLineService csvLineService;
    @Autowired
    private ObjectMapper objectMapper;


    @ServiceActivator
    public Message<String> process(Message<String> message) {
        log.info("\n\n ****** Inside LineMessageHandler.process(Message message) *****");
        log.info("Message to send: " + message);

        CsvLine csvLine = new CsvLine();
        csvLine.setLine(message.getPayload());
        csvLine.setFileName((String) message.getHeaders().get("file_name"));
        csvLine.setCorrelationId((UUID) message.getHeaders().get("correlationId"));
        csvLineService.saveCsvLine(csvLine);

        log.info(csvLine.toString());
        return message;
    }


}
