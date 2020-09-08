package mu.integration.producer;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 *
 * @author priteela
 */
@Component
public class LineProcessor {

    public Message process(String csvLine) throws Exception {

        System.out.println(csvLine);
        Message msg = MessageBuilder.withPayload(csvLine).build();
        return msg;

    }

}
