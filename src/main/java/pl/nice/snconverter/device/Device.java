package pl.nice.snconverter.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.nice.snconverter.customer.Customer;
import pl.nice.snconverter.media.Media;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "serial_number", nullable = false, length = 20)
    private String serialNumber;

    @Column(name = "dates_of_shipment")
    private LocalDate dateOfShipment;

    @Column(nullable = false)
    private String vatId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @OneToOne
    @JoinColumn(name = "media_id")
    Media media;

    public Device() {
        //JPA Only
    }
}
