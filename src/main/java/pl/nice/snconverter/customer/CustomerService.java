package pl.nice.snconverter.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nice.snconverter.AppConfig;
import pl.nice.snconverter.customer.dto.CustomerCreateDTO;
import pl.nice.snconverter.customer.dto.CustomerDTOMapper;
import pl.nice.snconverter.customer.dto.CustomerShowDTO;
import pl.nice.snconverter.customer.dto.CustomerUpdateDTO;
import pl.nice.snconverter.exception.ObjectNotFoundException;
import pl.nice.snconverter.exception.unique.IUnique;
import pl.nice.snconverter.message.MessageContent;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AppConfig appConfig;

    List<CustomerShowDTO> findAllByPage(int page, int recordsPerPage) {
        Pageable pageRequest = PageRequest.of(page - 1, recordsPerPage);
        return customerRepository.findAll(pageRequest).stream()
                .map(CustomerDTOMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    public List<CustomerShowDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerDTOMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    CustomerShowDTO findById(Long id) {
        return CustomerDTOMapper.entityToDtoShow(
                customerRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id))
        );
    }

    Customer update(CustomerUpdateDTO customerUpdateDTO, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id));

        return customerRepository.save(CustomerDTOMapper.dtoToEntityUpdate(customerUpdateDTO, customer));
    }

    Customer create(CustomerCreateDTO customerCreateDTO) {
        return customerRepository.save(CustomerDTOMapper.dtoToEntityCreate(customerCreateDTO));
    }

    void delete(Long id) {
        customerRepository.delete(
                customerRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id))
        );
    }

    Long count() {
        return customerRepository.count();
    }

}
