package pl.nice.snconverter.utils.urlfilter;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Data
@Component
public class URLFilter {
    private List<String> filter;

    public String getParam(String paramName) {
        return filter.stream()
                .filter(s -> s.split("=")[0].equals(paramName))
                .map(s -> s.split("=")[1].equals("undefined") ? "%" : s.split("=")[1])
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public LocalDate getParamAsDate(String paramName, LocalDate dateInstead) {
        return filter.stream()
                .filter(s -> s.split("=")[0].equals(paramName))
                .map(s -> s.split("=")[1].equals("undefined") ? dateInstead : LocalDate.parse(s.split("=")[1]))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getFilterChain(String filterPrefix) {
        return filterPrefix + "=" + String.join("%2C", filter).replace("=", "%3D");
    }
}
