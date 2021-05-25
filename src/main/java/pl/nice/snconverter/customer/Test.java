package pl.nice.snconverter.customer;

import org.springframework.stereotype.Component;
import pl.nice.snconverter.message.MessageContent;

@Component
public class Test {
    public String getString() {
        return MessageContent.EX_LAST_PAGE;
    }
}
