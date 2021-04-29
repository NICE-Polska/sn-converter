package pl.nice.snconverter.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.nice.snconverter.exception.unique.Operation;
import pl.nice.snconverter.exception.unique.Unique;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(length = 4, nullable = false, unique = true)
    private String idax;

    public Customer() {
        //JPA Only
    }
}
