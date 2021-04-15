package pl.nice.snconverter.device;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findDeviceBySerialNumber(@Param("serialNumber") String serialNumber);

    List<Device> findAllByCustomerIdax(@Param("idax")String idax);

    @Query("select d from Device d where (d.customer.idax like %:idax%)" +
            "and (d.customer.name like %:customer%)" +
            "and (d.vatId like %:vatId%)" +
            "and (d.name like %:name% )" +
            "and (d.serialNumber like %:serialNumber%)" +
            "and d.dateOfShipment >= :shipmentDateStart and d.dateOfShipment <= :shipmentDateEnd")
    List<Device> findAllDevicesByFilerParams(@Param("idax") String idax,
                                             @Param("customer") String customer,
                                             @Param("vatId") String vatId,
                                             @Param("name") String name,
                                             @Param("serialNumber") String serialNumber,
                                             @Param("shipmentDateStart")LocalDate shipmentDateStart,
                                             @Param("shipmentDateEnd") LocalDate shipmentDateEnd, Pageable pageRequest);

    @Query("select count(d) from Device d where (d.customer.idax like %:idax%)" +
            "and (d.customer.name like %:customer%)" +
            "and (d.vatId like %:vatId%)" +
            "and (d.name like %:name% )" +
            "and (d.serialNumber like %:serialNumber%)" +
            "and d.dateOfShipment >= :shipmentDateStart and d.dateOfShipment <= :shipmentDateEnd")
    Long countAllDevicesByFilerParams(@Param("idax") String idax,
                                      @Param("customer") String customer,
                                      @Param("vatId") String vatId,
                                      @Param("name") String name,
                                      @Param("serialNumber") String serialNumber,
                                      @Param("shipmentDateStart")LocalDate shipmentDateStart,
                                      @Param("shipmentDateEnd") LocalDate shipmentDateEnd);
}
