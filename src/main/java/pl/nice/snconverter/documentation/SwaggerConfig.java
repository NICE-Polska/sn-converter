package pl.nice.snconverter.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{

    public static final Contact DEFAULT_CONTACT = new Contact("Piotr Głowacki", "http://www.nice.pl", "p.glowacki@niceforyou.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("SN Converter", "RESTful Api Documentation", "0.0.0.alpha", "urn:tos",
            DEFAULT_CONTACT, "EULA", "http://localhost/eula", new ArrayList<VendorExtension>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json","application/xml"));

    @Bean
    public Docket api()
    {
        ApiInfo apiInfo;
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES).consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
