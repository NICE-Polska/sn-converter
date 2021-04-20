package pl.nice.snconverter.device;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nice.snconverter.AppConfig;
import pl.nice.snconverter.device.dto.DeviceCreateDTO;
import pl.nice.snconverter.device.dto.DeviceDTOMapper;
import pl.nice.snconverter.device.dto.DeviceShowDTO;
import pl.nice.snconverter.device.dto.DeviceUpdateDTO;
import pl.nice.snconverter.exception.NoResultsForQueryException;
import pl.nice.snconverter.exception.ObjectNotFoundException;
import pl.nice.snconverter.message.MessageContent;
import pl.nice.snconverter.utils.urlfilter.URLFilter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final URLFilter urlFilter;
    private final AppConfig appConfig;

    public List<DeviceShowDTO> findAllDevicesByFilterParams(int page, int recordsPerPage, List<String> filter) {
        urlFilter.setFilter(filter);
        Pageable pageRequest = PageRequest.of(page - 1, recordsPerPage);
        return deviceRepository.findAllDevicesByFilterParams(
                urlFilter.getParam("idax"),
                urlFilter.getParam("customer"),
                urlFilter.getParam("vatId"),
                urlFilter.getParam("name"),
                urlFilter.getParam("serialNumber"),
                urlFilter.getParamAsDate("shipmentDateStart", LocalDate.parse(appConfig.getConfigValues().getProperty("startDateToFilterQuery"))),
                urlFilter.getParamAsDate("shipmentDateEnd", LocalDate.now()), pageRequest).stream()
                .map(DeviceDTOMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    public List<DeviceShowDTO> findAllByPage(int page, int recordsPerPage) {
        Pageable pageRequest = PageRequest.of(page - 1, recordsPerPage);
        return deviceRepository.findAll(pageRequest).stream()
                .map(DeviceDTOMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    public DeviceShowDTO findById(Long id) {
        return DeviceDTOMapper.entityToDtoShow(
                deviceRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.DEVICE_NOT_FOUND + id))
        );
    }

    public DeviceShowDTO findDeviceBySerialNumber(String serialNumber) {
        return DeviceDTOMapper.entityToDtoShow(
                deviceRepository.findDeviceBySerialNumber(serialNumber)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.DEVICE_SN_NOT_FOUND + serialNumber))
        );
    }

    public Device update(DeviceUpdateDTO deviceUpdateDTO, Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.DEVICE_NOT_FOUND + id));

        return deviceRepository.save(DeviceDTOMapper.dtoToEntityUpdate(deviceUpdateDTO, device));
    }

    public Device create(DeviceCreateDTO deviceCreateDTO) {
        return deviceRepository.save(DeviceDTOMapper.dtoToEntityCreate(deviceCreateDTO));
    }

    public void delete(Long id) {
        deviceRepository.delete(
                deviceRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.DEVICE_NOT_FOUND + id))
        );
    }

    public Long count() {
        return deviceRepository.count();
    }

    public Long countAllDevicesByFilerParams(List<String> filter) {
        urlFilter.setFilter(filter);
        Long counter =  deviceRepository.countAllDevicesByFilerParams(
                urlFilter.getParam("idax"),
                urlFilter.getParam("customer"),
                urlFilter.getParam("vatId"),
                urlFilter.getParam("name"),
                urlFilter.getParam("serialNumber"),
                urlFilter.getParamAsDate("shipmentDateStart", LocalDate.parse(appConfig.getConfigValues().getProperty("startDateToFilterQuery"))),
                urlFilter.getParamAsDate("shipmentDateEnd", LocalDate.now()));
        if (counter == 0) throw new NoResultsForQueryException(MessageContent.EX_NO_RECORDS_FOUND);
        return counter;
    }
}
//TODO parametr recordsOnPage przenieśc do serwisu (nie musi byc przekazywany z kontrolera)
//TODO dodac exception noResultsForQuery (zeby nie obslugiwac braku wynikow przez PageNumberToHigh - najlepiej w countAllDevicesByFilerParams
//TODO kaskady
