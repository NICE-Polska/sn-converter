package pl.nice.snconverter.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.nice.snconverter.media.AcceptedMediaType;
import pl.nice.snconverter.message.MessageContent;
import pl.nice.snconverter.response.FieldsToMapConverter;
import pl.nice.snconverter.response.ResponseDetails;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestExceptionAdvice {
    private final FieldsToMapConverter<ResponseDetails> fieldsToMapConverter;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleObjectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .status(HttpStatus.NOT_FOUND.value())
                .description(ex.getMessage())
                .url(request.getRequestURL().toString())
                .build());

        return createResponse(errorsList, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<Object> errorsList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                e -> errorsList.add(ResponseErrorDetails.builder()
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .description(MessageContent.VALID_FIELD_VALID + e.getField() + ". " + e.getDefaultMessage())
                        .url(request.getRequestURL().toString())
                        .build())
        );

        return createResponse(errorsList, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageNumberTooHighException.class)
    ResponseEntity<Map<String, Object>> handlePageNumberTooHighException(PageNumberTooHighException ex, HttpServletRequest request) {
        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .description(ex.getMessage())
                .url(request.getRequestURL().toString() + MessageContent.PAGE_IN_URL + ex.getPageNumber())
                .build());

        return createResponse(errorsList, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DatabaseTableEmptyException.class)
    ResponseEntity<Map<String, Object>> handleDatabaseTableEmptyException(DatabaseTableEmptyException ex, HttpServletRequest request) {
        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(MessageContent.NO_ITEMS_IN_TABLE)
                .status(HttpStatus.NOT_FOUND.value())
                .description(ex.getMessage())
                .url(request.getRequestURL().toString())
                .build());

        return createResponse(errorsList, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadFileTypeException.class)
    ResponseEntity<Map<String, Object>> handleBadFileTypeException(BadFileTypeException ex, HttpServletRequest request) {

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .errorCode("0xBFT") //0 - runtime ex, BFT BadFileType
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .description(MessageContent.MEDIA_ACCEPTED_FILE
                        + Arrays.toString(AcceptedMediaType.values()))
                .url(request.getRequestURL().toString())
                .build());

        return createResponse(errorsList, HttpStatus.BAD_REQUEST);

    }

    private ResponseEntity<Map<String, Object>> createResponse(List<Object> errorsList, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(fieldsToMapConverter.getFieldsAsMap(
                        ResponseDetails.builder()
                                .status(MessageContent.ERROR)
                                .errors(errorsList)
                                .build()
                ));
    }
}
//TODO dorobić kod blędu - jakiś liczbowo cyfrowy
//TODO sprawdzic co idzie w messagach i description
