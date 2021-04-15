package pl.nice.snconverter.device.dto;

import lombok.Builder;
import lombok.Value;
import pl.nice.snconverter.customer.dto.CustomerShowDTO;

import java.time.LocalDate;

@Value
@Builder
public class DeviceShowDTO {

    Long id;

    String name;

    String serialNumber;

    byte[] image;

    LocalDate dateOfShipment;

    String vatId;

    CustomerShowDTO customer;
}
