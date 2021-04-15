package pl.nice.snconverter.media;

import lombok.Getter;

@Getter
public enum AcceptedMediaType {
    TIFF("image/tif"),
    JPEG("image/jpg"),
    GIF("image/gif"),
    BMP("image/bmp"),
    PNG("image/png");

    String mediaType;

    AcceptedMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
