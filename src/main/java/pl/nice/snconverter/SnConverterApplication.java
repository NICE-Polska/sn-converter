package pl.nice.snconverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnConverterApplication {
    private static final Logger logger
            = LoggerFactory.getLogger(SnConverterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SnConverterApplication.class, args);
        logger.info("Application start from class {}", SnConverterApplication.class.getSimpleName());
    }
}
