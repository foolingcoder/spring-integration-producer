package mu.integration.producer;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 *
 * @author priteela
 */
@Component
public class LineMessageProcessor {

    @ServiceActivator
    public Message<String> process(Message<String> message) {
        System.out.println("Inside process(Message message)");

        System.out.println("Reply: " + message);

        return message;
    }


}
