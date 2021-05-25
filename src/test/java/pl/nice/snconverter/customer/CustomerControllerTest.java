package pl.nice.snconverter.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.nice.snconverter.AppConfig;
import pl.nice.snconverter.customer.dto.CustomerCreateDTO;
import pl.nice.snconverter.customer.dto.CustomerDTOMapper;
import pl.nice.snconverter.customer.dto.CustomerShowDTO;
import pl.nice.snconverter.paging.PageAdvice;
import pl.nice.snconverter.response.FieldsToMapConverter;
import pl.nice.snconverter.response.ResponseDetails;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private FieldsToMapConverter<ResponseDetails> fieldsToMapConverter;
    @MockBean
    private PageAdvice pageAdvice;
    @MockBean
    private AppConfig appConfig;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private pl.nice.snconverter.customer.Test test;
    @MockBean
    ResponseDetails responseDetails;

    @Test
    @DisplayName("POST /customers")
    void testCreateCustomer() throws Exception {
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO("Test", "4444");

        Customer customer = new Customer();
        customer.setIdax("4444");
        customer.setName("Test");
        customer.setId(1L);
        responseDetails = ResponseDetails.builder().data(customer).build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("Key", customer);
        when(customerService.create(customerCreateDTO)).thenReturn(customer);
        //when(fieldsToMapConverter.getFieldsAsMap(ResponseDetails.builder().status("Ok").build())).thenReturn(map);

        mockMvc.perform(post("/customers")
                .content(objectMapper.writeValueAsString(customerCreateDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/customers/1"))
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Test
    @DisplayName("GET /customers/1")
    void testGetCustomerById() throws Exception {
        CustomerShowDTO customerShowDTO = CustomerShowDTO.builder()
                .id(1L)
                .idax("4444")
                .name("Test")
                .build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", "ok");
        map.put("data", customerShowDTO);
        when(fieldsToMapConverter.getFieldsAsMap(ResponseDetails.builder().status("Ok").build())).thenReturn(map);

        mockMvc.perform(get("/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.idax", is("4444")))
                .andExpect(jsonPath("@.data.name", is("Test")))
                .andExpect(jsonPath("$.status", is("ok")));
    }

    @Test
    @DisplayName("GET /test")
    void testGet() throws Exception {
        //when(customerService.create(customerCreateDTO)).thenReturn(customer);
        when(test.getString()).thenReturn("Tesckik");
        mockMvc.perform(get("/customers/test")
                .content(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated());
    }

}
