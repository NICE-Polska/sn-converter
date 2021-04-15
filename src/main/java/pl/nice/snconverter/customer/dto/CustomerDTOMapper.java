package pl.nice.snconverter.customer.dto;

import pl.nice.snconverter.customer.Customer;

public class CustomerDTOMapper {
    private CustomerDTOMapper() {
        throw new IllegalStateException(this.getClass().getName());
    }

    public static CustomerShowDTO entityToDtoShow(Customer customer) {
        return CustomerShowDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .idax(customer.getIdax())
                .build();
    }

    public static CustomerShowInForeignDTO entityToForeignDto(Customer customer) {
        return CustomerShowInForeignDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .build();
    }

    public static CustomerIdOnlyDTO entityToDtoOnlyId(Customer customer) {
        return CustomerIdOnlyDTO.builder()
                .id(customer.getId())
                .build();
    }

    public static Customer dtoToEntityUpdate(CustomerUpdateDTO customerUpdateDTO, Customer customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customerUpdateDTO.getName())
                .idax(customerUpdateDTO.getIdax())
                .build();
    }

    public static Customer dtoToEntityCreate(CustomerCreateDTO customerCreateDTO) {
        return Customer.builder()
                .name(customerCreateDTO.getName())
                .idax(customerCreateDTO.getIdax())
                .build();
    }

    public static Customer dtoToEntityIdOnly(CustomerIdOnlyDTO customerIdOnlyDTO) {
        return Customer.builder()
                .id(customerIdOnlyDTO.getId())
                .build();
    }
}
