package pl.nice.snconverter.device.dto;

import pl.nice.snconverter.customer.dto.CustomerDTOMapper;
import pl.nice.snconverter.device.Device;
import pl.nice.snconverter.media.dto.MediaDtoMapper;

import java.util.Optional;

public class DeviceDTOMapper {
    private DeviceDTOMapper() {
        throw new IllegalStateException(this.getClass().getName());
    }

    public static DeviceShowDTO entityToDtoShow(Device device) {
        return DeviceShowDTO.builder()
                .id(device.getId())
                .name(device.getName())
                .dateOfShipment(device.getDateOfShipment())
                .serialNumber(device.getSerialNumber())
                .vatId(device.getVatId())
                .customer(
                        Optional.ofNullable(device.getCustomer())
                                .map(CustomerDTOMapper::entityToDtoShow)
                                .orElse(null)
                )
                .build();
    }

    public static DeviceIdOnlyDTO entityToDtoOnlyId(Device device) {
        return DeviceIdOnlyDTO.builder()
                .id(device.getId())
                .build();
    }

    public static Device dtoToEntityUpdate(DeviceUpdateDTO deviceUpdateDTO, Device device) {
        return Device.builder()
                .id(device.getId())
                .name(deviceUpdateDTO.getName())
                .dateOfShipment(deviceUpdateDTO.getDateOfShipment())
                .serialNumber(deviceUpdateDTO.getSerialNumber())
                .vatId(deviceUpdateDTO.getVatId())
                .customer(
                        Optional.ofNullable(deviceUpdateDTO.getCustomer())
                                .map(CustomerDTOMapper::dtoToEntityIdOnly)
                                .orElse(null)
                )
                .build();
    }

    public static Device dtoToEntityCreate(DeviceCreateDTO deviceCreateDTO) {
        return Device.builder()
                .name(deviceCreateDTO.getName())
                .dateOfShipment((deviceCreateDTO.getDateOfShipment()))
                .serialNumber(deviceCreateDTO.getSerialNumber())
                .vatId(deviceCreateDTO.getVatId())
                .customer(
                        Optional.ofNullable(deviceCreateDTO.getCustomer())
                                .map(CustomerDTOMapper::dtoToEntityIdOnly)
                                .orElse(null)
                )
                .media(
                        Optional.ofNullable(deviceCreateDTO.getMedia())
                        .map(MediaDtoMapper::dtoToEntityOnlyId)
                        .orElse(null)
                )
                .build();
    }
}
