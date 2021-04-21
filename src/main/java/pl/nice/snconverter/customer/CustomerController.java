package pl.nice.snconverter.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.nice.snconverter.AppConfig;
import pl.nice.snconverter.customer.dto.CustomerCreateDTO;
import pl.nice.snconverter.customer.dto.CustomerDTOMapper;
import pl.nice.snconverter.customer.dto.CustomerUpdateDTO;
import pl.nice.snconverter.exception.PageNumberTooHighException;
import pl.nice.snconverter.message.MessageContent;
import pl.nice.snconverter.paging.PageAdvice;
import pl.nice.snconverter.response.FieldsToMapConverter;
import pl.nice.snconverter.response.ResponseDetails;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final FieldsToMapConverter<ResponseDetails> fieldsToMapConverter;
    private final PageAdvice pageAdvice;
    private final AppConfig appConfig;

    @GetMapping(params = "page")
    public ResponseEntity<Map<String, Object>> findBAllyPage(@RequestParam int page, HttpServletRequest request) {
        int recordsOnPage = Integer.parseInt(appConfig.getConfigValues().getProperty("recordsPerPage" +
                ""));

        int totalRecords = Math.toIntExact(customerService.count());
        pageAdvice.setRecordsPerPage(recordsOnPage);

        if(page > pageAdvice.getTotalPages(totalRecords))
            throw new PageNumberTooHighException(
                    MessageContent.EX_PAGE_NUM_TO_HIGH + pageAdvice.getTotalPages(totalRecords), page);

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .data(customerService.findAllByPage(page, pageAdvice.getRecordsPerPage()))
                                .pageNumber(page)
                                .totalPages(pageAdvice.getTotalPages(totalRecords))
                                .nextPage(pageAdvice.getNextPageURL(pageAdvice.getTotalPages(totalRecords), page, request))
                                .build()
                ));
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> findAll() {
        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(customerService.findAll())
                        .build()
                ));
    }

    @GetMapping("/{id}")
   public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .data(customerService.findById(id))
                                .build()
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody CustomerUpdateDTO customerUpdateDTO, @PathVariable Long id) {
        Customer customer = customerService.update(customerUpdateDTO, id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .message(MessageContent.CUSTOMER_UPDATED + customer.getId())
                                .data(CustomerDTOMapper.entityToDtoShow(customer))
                                .url(location.toString())
                                .build()
                ));
    }

    @PostMapping ()
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CustomerCreateDTO customerCreateDTO) {
        Customer customer = customerService.create(customerCreateDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();

        return ResponseEntity.created(location).body(fieldsToMapConverter.getFieldsAsMap(
                ResponseDetails.builder()
                        .message(MessageContent.OK)
                        .data(CustomerDTOMapper.entityToDtoOnlyId(customer))
                        .location(location)
                        .build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete (@PathVariable Long id) {
        customerService.delete(id);

        return ResponseEntity.ok()
                .body(
                        fieldsToMapConverter.getFieldsAsMap(
                                ResponseDetails.builder()
                                        .status(MessageContent.OK)
                                        .message(MessageContent.CUSTOMER_DELETED + id)
                                        .build()
                        )
                );
    }

    @GetMapping("/quantity")
    public ResponseEntity<Map<String, Object>> count() {
        Map<String, Long> dataResponse = new HashMap<>();
        dataResponse.put(MessageContent.ITEMS, customerService.count());

        return ResponseEntity.ok()
                .body(
                        fieldsToMapConverter.getFieldsAsMap(
                                ResponseDetails.builder()
                                        .status(MessageContent.OK)
                                        .data(dataResponse)
                                        .build()
                        )
                );
    }
}
//TODO przenisć sprawdzanie ilosci stron do serwisu
//TODO zastanowic sie jak w metodzie save zwrocic status i message
//TODO wyrzucic / w findAll
