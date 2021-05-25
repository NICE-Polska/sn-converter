package pl.nice.snconverter.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PageAdviceTest {
    @Autowired
    private PageAdvice pageAdvice;
    @Autowired
    private HttpServletRequest request;

   @Test
    void shouldReturnLinkWithPageNumberEqualToTwo() {
        //given
        long totalPages = 10;
        int page = 1;
        //when
        String result = pageAdvice.getNextPageURL(totalPages, page, request);
        //then
        assertEquals("http://localhost?page=2", result);
    }

    @Test
    void shouldReturnNull() {
       //given
        long totalPages = 10;
        int page = 10;
        //when
        String result = pageAdvice.getNextPageURL(totalPages, page, request);
        //then
        assertNull(result);
    }

    @Test
    void shouldReturnNumbersOfTotalPagesEqualToTen() {
       //given
        int totalRecords = 100;
        pageAdvice.setRecordsPerPage(10);
        //when
        int result = pageAdvice.getTotalPages(totalRecords);
        //then
        assertEquals(10, result);
    }

    @Test
    void shouldReturnNumbersOfTotalPagesEqualToEleven() {
        //given
        int totalRecords = 101;
        pageAdvice.setRecordsPerPage(10);
        //when
        int result = pageAdvice.getTotalPages(totalRecords);
        //then
        assertEquals(11, result);
    }
}
