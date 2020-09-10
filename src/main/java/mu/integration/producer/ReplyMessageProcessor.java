package mu.integration.producer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.dto.CsvLineDto;

/**
 *
 * @author priteela
 */
@Slf4j
@Component
public class ReplyMessageProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator
    public String process(byte[] byteMessage) {
        System.out.println("\n\n ****** Inside ReplyMessageProcessor.process((byte[] byteMessage)  *****");

        CsvLineDto csvLineDto = null;
        try {
            csvLineDto = objectMapper.readValue(byteMessage, CsvLineDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String csvLine = csvLineDto.toString();
        System.out.println("Reply received: " + csvLine);
        return csvLine;

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
