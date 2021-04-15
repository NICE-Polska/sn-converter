package pl.nice.snconverter;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
@Data
public class AppConfig {
    private static final String CONFIG_FILE = "config.properties";
    private static final String MESSAGE_FILE = "messages.properties";

    public Properties getConfigValues() {
        return getProperties(CONFIG_FILE);
    }

    public Properties getMessageValues() {
        return getProperties(MESSAGE_FILE);
    }

    private Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)){
            properties.load(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return properties;
    }
}
