package pl.nice.snconverter.paging;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.nice.snconverter.AppConfig;
import pl.nice.snconverter.message.MessageContent;
import javax.servlet.http.HttpServletRequest;

@Data
@Component
public class PageAdvice {
    private int recordsPerPage;
    private final AppConfig appConfig;

    public String getNextPageURL(long totalPages, int page, HttpServletRequest request) {
        if(totalPages != page)
            return request.getRequestURL().toString() + "?" + appConfig.getConfigValues().getProperty("pageNameInUrl") + "=" + ++page;

        return null;
    }

    public String getNextPageURL(long totalPages, String params, int page, HttpServletRequest request) {
        if(totalPages != page)
            return request.getRequestURL().toString() + "?" + params + "&" + appConfig.getConfigValues().getProperty("pageNameInUrl") + "=" + ++page;

        return null;
    }

    public int getTotalPages(int totalRecords) {
        if(totalRecords % recordsPerPage == 0)
            return totalRecords / recordsPerPage;

        return (totalRecords / recordsPerPage) + 1;
    }
}
//TODO poprawić wyświetlanie sciezki w exception
