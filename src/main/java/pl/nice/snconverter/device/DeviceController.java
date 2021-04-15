package pl.nice.snconverter.device;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.nice.snconverter.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nice.snconverter.device.dto.DeviceCreateDTO;
import pl.nice.snconverter.device.dto.DeviceDTOMapper;
import pl.nice.snconverter.device.dto.DeviceUpdateDTO;
import pl.nice.snconverter.exception.PageNumberTooHighException;
import pl.nice.snconverter.message.MessageContent;
import pl.nice.snconverter.paging.PageAdvice;
import pl.nice.snconverter.response.FieldsToMapConverter;
import pl.nice.snconverter.response.ResponseDetails;
import pl.nice.snconverter.utils.urlfilter.URLFilter;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final FieldsToMapConverter<ResponseDetails> fieldsToMapConverter;
    private final PageAdvice pageAdvice;
    private final URLFilter urlFilter;
    private final AppConfig appConfig;

    @GetMapping(params = {"filter", "page"})
    ResponseEntity<Map<String, Object>> findAllDevicesByFilterParams(
            @RequestParam List<String> filter, @RequestParam int page, HttpServletRequest request) {
        int recordsOnPage = Integer.parseInt(appConfig.getConfigValues().getProperty("recordsPerPage"));

        int totalRecords = Math.toIntExact(deviceService.countAllDevicesByFilerParams(filter));
        pageAdvice.setRecordsPerPage(recordsOnPage);

        if(page > pageAdvice.getTotalPages(totalRecords))
            throw new PageNumberTooHighException(
                    MessageContent.PAGE_NUM_TO_HIGH + pageAdvice.getTotalPages(totalRecords), page);
        urlFilter.setFilter(filter);
        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(deviceService.findAllDevicesByFilterParams(page, pageAdvice.getRecordsPerPage(), filter))
                        .pageNumber(page)
                        .totalPages(pageAdvice.getTotalPages(totalRecords))
                        .nextPage(pageAdvice.getNextPageURL(pageAdvice.getTotalPages(totalRecords), urlFilter.getFilterChain(appConfig.getConfigValues().getProperty("filterPrefix")), page, request ))
                        .build()
                ));
    }

    /*@GetMapping(params = "page")
    ResponseEntity<Map<String, Object>> findBAllyPage(@RequestParam int page, HttpServletRequest request) {
        int recordsOnPage = Integer.parseInt(appConfig.getConfigValues().getProperty("recordsPerPage"));

        int totalRecords = Math.toIntExact(deviceService.count());
        pageAdvice.setRecordsPerPage(recordsOnPage);

        if(page > pageAdvice.getTotalPages(totalRecords))
            throw new PageNumberTooHighException(
                    MessageContent.PAGE_NUM_TO_HIGH + pageAdvice.getTotalPages(totalRecords), page);

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .data(deviceService.findAllByPage(page, pageAdvice.getRecordsPerPage()))
                                .pageNumber(page)
                                .totalPages(pageAdvice.getTotalPages(totalRecords))
                                .nextPage(pageAdvice.getNextPageURL(pageAdvice.getTotalPages(totalRecords), page, request))
                                .build()
                ));
    }*/

    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .data(deviceService.findById(id))
                                .build()
                ));
    }
    @GetMapping("/serial-number/{serialNumber}")
    ResponseEntity<Map<String, Object>> findDeviceBySerialNUmber(@PathVariable String serialNumber) {

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .data(deviceService.findDeviceBySerialNumber(serialNumber))
                                .build()
                ));
    }

    @PutMapping("/{id}")
    ResponseEntity<Map<String, Object>> update(@Valid @RequestBody DeviceUpdateDTO deviceUpdateDTO, @PathVariable Long id) {
        Device device = deviceService.update(deviceUpdateDTO, id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .message(MessageContent.DEVICE_UPDATED + device.getId())
                                .data(DeviceDTOMapper.entityToDtoShow(device))
                                .url(location.toString())
                                .build()
                ));
    }

    @PostMapping ()
    ResponseEntity<Map<String, Object>> create(@Valid @RequestBody DeviceCreateDTO deviceCreateDTO) {
        Device device = deviceService.create(deviceCreateDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(device.getId())
                .toUri();

        return ResponseEntity.created(location).body(fieldsToMapConverter.getFieldsAsMap(
                ResponseDetails.builder()
                        .message(MessageContent.OK)
                        .data(DeviceDTOMapper.entityToDtoOnlyId(device))
                        .location(location)
                        .build()));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Object>> delete (@PathVariable Long id) {
        deviceService.delete(id);

        return ResponseEntity.ok()
                .body(
                        fieldsToMapConverter.getFieldsAsMap(
                                ResponseDetails.builder()
                                        .status(MessageContent.OK)
                                        .message(MessageContent.DEVICE_DELETED + id)
                                        .build()
                        )
                );
    }

    @GetMapping("/quantity")
    ResponseEntity<Map<String, Object>> count() {
        Map<String, Long> dataResponse = new HashMap<>();
        dataResponse.put(MessageContent.ITEMS, deviceService.count());

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
//TODO przeniesc sprawdzanie pageNumber do serwisu
