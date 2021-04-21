package pl.nice.snconverter.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
public class CustomerService implements IUnique {
    private final CustomerRepository customerRepository;
    private final AppConfig appConfig;

    public List<CustomerShowDTO> findAllByPage(int page, int recordsPerPage) {
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

    public CustomerShowDTO findById(Long id) {
        return CustomerDTOMapper.entityToDtoShow(
                customerRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id))
        );
    }

    public Customer update(CustomerUpdateDTO customerUpdateDTO, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id));

        return customerRepository.save(CustomerDTOMapper.dtoToEntityUpdate(customerUpdateDTO, customer));
    }

    public Customer create(CustomerCreateDTO customerCreateDTO) {
        return customerRepository.save(CustomerDTOMapper.dtoToEntityCreate(customerCreateDTO));
    }

    public void delete(Long id) {
        customerRepository.delete(
                customerRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id))
        );
    }

    public Long count() {
        return customerRepository.count();
    }

    @Override
    public boolean checkUnique(String columnName, String value) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher(columnName, ExampleMatcher.GenericPropertyMatchers.ignoreCase());

        Customer customer = new Customer();
        customer.setIdax(value);
        Example<Customer> example = Example.of(customer, matcher);
        return !customerRepository.exists(example);
    }
}
