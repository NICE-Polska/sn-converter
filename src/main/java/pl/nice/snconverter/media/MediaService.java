package pl.nice.snconverter.media;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.nice.snconverter.customer.dto.CustomerDTOMapper;
import pl.nice.snconverter.exception.BadFileTypeException;
import pl.nice.snconverter.exception.ObjectNotFoundException;
import pl.nice.snconverter.media.dto.MediaDtoMapper;
import pl.nice.snconverter.media.dto.MediaShowDTO;
import pl.nice.snconverter.message.MessageContent;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    MediaShowDTO findById(Long id) {
        return MediaDtoMapper.entityToDtoShow(
                mediaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.MEDIA_NOT_FOUND + id)));
    }

    Media create(MultipartFile file) throws IOException {
        if(!isAcceptedMediaType(file.getContentType()))
            throw new BadFileTypeException(MessageContent.MEDIA_BAD_FILE);

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "." +
                Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

        Media media = new Media();
        media.setFile(file.getBytes());
        media.setOriginName(file.getOriginalFilename());
        media.setType(file.getContentType());
        media.setUuidFileName(fileName);
        return mediaRepository.save(media);
    }

    String doOcr(MultipartFile file) {
        if(!isAcceptedMediaType(file.getContentType()))
            throw new BadFileTypeException(MessageContent.MEDIA_BAD_FILE);

        try {
            //URL imageFile = new URL(url); // 1
            InputStream stream = new ByteArrayInputStream(file.getBytes());
            BufferedImage bufferedImage = ImageIO.read(stream);
            ITesseract instance = new Tesseract();
            instance.setDatapath("C:\\Users\\pglow\\Downloads");
            instance.setLanguage("pol");
            return instance.doOCR(bufferedImage).replace("\n", " ").trim();
        } catch (Exception ex) {
            // TODO Exception handler
            ex.printStackTrace();
        }
        return null;
    }

    void delete(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.MEDIA_NOT_FOUND + id));
        mediaRepository.deleteById(media.getId());
    }

    Long count() {
        return mediaRepository.count();
    }

    private boolean isAcceptedMediaType(String mediaType) {
        AcceptedMediaType[] acceptedMediaTypes = AcceptedMediaType.values();
        return  Arrays.stream(acceptedMediaTypes)
                .anyMatch(x -> x.getMediaType().equals(mediaType));
    }
}
//TODO poprwić location z url w messagach
