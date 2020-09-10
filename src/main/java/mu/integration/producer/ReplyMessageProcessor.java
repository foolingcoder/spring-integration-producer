package mu.integration.producer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import mu.integration.producer.dto.CsvLineDto;

/**
 *
 * @author priteela
 */
@Component
public class ReplyMessageProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    public void process(Message message) {
        System.out.println("Inside process(Message message)");

        System.out.println("Reply: " + message);
    }


    public String process(byte[] message) {
        //  System.out.println("Inside process((byte[] message)");

        // System.out.println("Reply: " + convertFromBytesMessage(message));

        String reply = convertFromBytesMessage(message);
        //  System.out.println(reply);


        CsvLineDto csvLineDto = null;
        try {
            csvLineDto = objectMapper.readValue(message, CsvLineDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(csvLineDto);

        return csvLineDto.toString();

    }

    protected String convertFromBytesMessage(byte[] bytes) {
        String s = null;
        try {
            s = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new MessageConversionException("Cannot convert bytes to String", ex);
        }
        return s;
    }

}
