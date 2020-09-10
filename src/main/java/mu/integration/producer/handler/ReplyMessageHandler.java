package mu.integration.producer.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.dto.CsvLineStatusDto;

/**
 *
 * @author priteela
 */
@Slf4j
@Component
public class ReplyMessageHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator
    public String process(byte[] byteMessage) {
        log.info("\n\n ****** Inside ReplyMessageHandler.process(byte[] byteMessage)  *****");
        log.info("Reply received: " + convertFromBytesMessage(byteMessage));

        CsvLineStatusDto csvLineStatusDto = null;
        try {
            csvLineStatusDto = objectMapper.readValue(byteMessage, CsvLineStatusDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String csvLineStatu = csvLineStatusDto.toString();
        log.info("Reply received: " + csvLineStatu);
        return csvLineStatu;

    }

    private String convertFromBytesMessage(byte[] bytes) {
        String str = null;
        try {
            str = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new MessageConversionException("Cannot convert bytes to String", ex);
        }
        return str;
    }

}
