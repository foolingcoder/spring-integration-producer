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
        System.out.println("\n\n ****** Inside LineMessageProcessor.process(Message message) *****");

        System.out.println("Message to send: " + message);

        return message;
    }


}
