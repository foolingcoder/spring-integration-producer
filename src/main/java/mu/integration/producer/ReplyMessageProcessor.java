package mu.integration.producer;

import java.nio.charset.StandardCharsets;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

/**
 *
 * @author priteela
 */
@Component
public class ReplyMessageProcessor {


    public void process(Message message) {
        System.out.println("Inside process(Message message)");

        System.out.println("Reply: " + message);
    }


    public void process(byte[] message) {
        System.out.println("Inside process((byte[] message)");

        System.out.println("Reply: " + convertFromBytesMessage(message));
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
