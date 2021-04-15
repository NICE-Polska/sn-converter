package pl.nice.snconverter.media;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.nice.snconverter.customer.CustomerService;
import pl.nice.snconverter.media.dto.MediaDtoMapper;
import pl.nice.snconverter.message.MessageContent;
import pl.nice.snconverter.response.FieldsToMapConverter;
import pl.nice.snconverter.response.ResponseDetails;
import pl.nice.snconverter.utils.report.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;

import java.util.*;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaService mediaService;
    private final FieldsToMapConverter<ResponseDetails> fieldsToMapConverter;
    private final CustomerService customerService;

    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.OK)
                                .data(mediaService.findById(id))
                                .build()
                ));
    }

    @PostMapping()
    ResponseEntity<Map<String, Object>> create(@RequestBody MultipartFile file) throws IOException {
        Media media = mediaService.create(file);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(media.getId())
                .toUri();

        return ResponseEntity.created(location).body(fieldsToMapConverter.getFieldsAsMap(
                ResponseDetails.builder()
                        .message(MessageContent.OK)
                        .data(MediaDtoMapper.entityToDtoOnlyId(media))
                        .location(location)
                .build()));
    }

    @PostMapping("/ocr")
    ResponseEntity<Map<String, Object>> doOcr(@RequestBody MultipartFile file) {
        Map<String, Object> dataResponse = new HashMap<>();
        dataResponse.put(MessageContent.SERIAL_NUMBER, mediaService.doOcr(file));

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

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        mediaService.delete(id);
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
        dataResponse.put(MessageContent.ITEMS, mediaService.count());
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

    @GetMapping("/test")
    void test(HttpServletResponse response) {

        Report report = ReportFactory.builder()
                .reportType(ReportType.EXCEL)
                .build();

        List<String> headers = new ArrayList<>();
        headers.add("Klient");
        headers.add("IDAX");
        List<String> data = new ArrayList<>();
        customerService.findAll().forEach(
                r -> data.add(r.getName() + "," + r.getIdax())
        );
        report.setData(data);
        report.setHeadersNames(headers);
        report.createReport();

        response.addHeader("Content-disposition", "attachment;filename=temp1123.xlsx");
        //response.setContentType("/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentType("new MediaType(\"application\", \"force-download\")");
        InputStream inputStream = new ByteArrayInputStream(report.getReportBytes());
        try {
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value="/downloadTemplate")
    public ResponseEntity<ByteArrayResource> downloadTemplate() throws Exception {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(); // creates the workbook
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProductTemplate.xlsx");
            workbook.write(stream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            //log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
