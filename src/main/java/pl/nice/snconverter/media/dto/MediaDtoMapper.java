package pl.nice.snconverter.media.dto;

import pl.nice.snconverter.media.Media;

public class MediaDtoMapper {
    private MediaDtoMapper() {
        throw new IllegalStateException(this.getClass().getName());
    }

    public static MediaOnlyIdDTO entityToDtoOnlyId (Media media) {
        return MediaOnlyIdDTO.builder()
                .id(media.getId())
                .build();
    }

    public static Media dtoToEntityOnlyId (MediaOnlyIdDTO mediaOnlyIdDto) {
        return Media.builder()
                .id(mediaOnlyIdDto.getId())
                .build();
    }

    public static MediaShowDTO entityToDtoShow (Media media) {
        return MediaShowDTO.builder()
                .id(media.getId())
                .file(media.getFile())
                .originName(media.getOriginName())
                .type(media.getType())
                .uuidFileName(media.getUuidFileName())
                .build();
    }
}
