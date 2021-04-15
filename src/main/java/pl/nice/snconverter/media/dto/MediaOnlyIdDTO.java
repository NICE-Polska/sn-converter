package pl.nice.snconverter.media.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MediaOnlyIdDTO {
    Long id;

    @JsonCreator
    public MediaOnlyIdDTO(@JsonProperty("id") Long id) {
        this.id = id;
    }
}
