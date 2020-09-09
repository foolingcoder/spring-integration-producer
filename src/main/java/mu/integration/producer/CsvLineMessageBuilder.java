package mu.integration.producer;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Builds a corresponding {@link Message} for each csv line
 *
 * @author Priteela
 */
@Component
public class CsvLineMessageBuilder {

    public Message build(String csvLine) throws Exception {

        Message msg = MessageBuilder.withPayload(csvLine).build();

        return msg;

    }

}
