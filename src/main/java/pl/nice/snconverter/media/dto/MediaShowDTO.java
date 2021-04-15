package pl.nice.snconverter.media.dto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MediaShowDTO {

    Long id;

    String originName;


    String uuidFileName;

    String type;

    byte[] file;
}
