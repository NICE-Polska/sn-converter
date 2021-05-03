package pl.nice.snconverter.customer.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nice.snconverter.customer.Customer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerDTOMapperTest {

    @Test
    void shouldReturnCustomerShowDTOModel() {
        //given
        Customer customer = new Customer();
        //when
        CustomerShowDTO customerShowDTO = CustomerDTOMapper.entityToDtoShow(customer);
        //then
        assertNotNull(customerShowDTO);
    }

    @Test
    void CustomerShowDTOFieldsShouldBeSameAsGivenCustomerFields() {
        //given
        Customer customer = new Customer();
        customer.setName("Test");
        customer.setIdax("4444");
        customer.setId(1L);
        //when
        CustomerShowDTO customerShowDTO = CustomerDTOMapper.entityToDtoShow(customer);
        //then
        assertEquals (1L, customerShowDTO.getId());
        assertEquals( "4444", customerShowDTO.getIdax());
        assertEquals( "Test", customerShowDTO.getName());
    }

    @Test
    void shouldReturnCustomerShowInForeignDTOModel() {
        //given
        Customer customer = new Customer();
        //when
        CustomerShowInForeignDTO customerShowInForeignDTO = CustomerDTOMapper.entityToForeignDto(customer);
        //then
        assertNotNull(customerShowInForeignDTO);
    }

    @Test
    void CustomerShowInForeignDTOFieldsShouldBeSameAsGivenCustomerFields() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test");
        //when
        CustomerShowInForeignDTO customerShowInForeignDTO = CustomerDTOMapper.entityToForeignDto(customer);
        //then
        assertEquals( 1L, customerShowInForeignDTO.getId());
        assertEquals("Test", customerShowInForeignDTO.getName());
    }

    @Test
    void shouldReturnCustomerIdOnlyDTOModel() {
        //given
        Customer customer = new Customer();
        //when
        CustomerIdOnlyDTO customerIdOnlyDTO = CustomerDTOMapper.entityToDtoOnlyId(customer);
        //then
        assertNotNull(customerIdOnlyDTO);
    }

    @Test
    void CustomerIdOnlyDTOFieldsShouldBeSameAsGivenCustomerFields() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        //when
        CustomerIdOnlyDTO customerIdOnlyDTO = CustomerDTOMapper.entityToDtoOnlyId(customer);
        //then
        assertEquals( 1L, customerIdOnlyDTO.getId());
    }

    @Test
    void shouldReturnCustomerModel() {
        //given
        Customer customer = new Customer();
        CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO("Test", "4444");
        //when
        Customer customer1 = CustomerDTOMapper.dtoToEntityUpdate(customerUpdateDTO, customer);
        //then
        assertNotNull(customer1);
    }

    @Test
    void CustomerFieldsShouldBeSameAsGivenCustomerUpdateDTO() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO("Test", "4444");
        //when
        Customer customer1 = CustomerDTOMapper.dtoToEntityUpdate(customerUpdateDTO, customer);
        //then
        assertEquals( 1L, customer1.getId());
        assertEquals("4444", customer1.getIdax());
        assertEquals("Test", customer1.getName());
    }

    @Test
    void shouldReturnCustomerModelBuildFromCustomerCreateDTO() {
        //given
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO("Test", "4444");
        //when
        Customer customer = CustomerDTOMapper.dtoToEntityCreate(customerCreateDTO);
        //then
        assertNotNull(customer);
    }

    @Test
    void CustomerFieldsShouldBeSameAsGivenCustomerCreateDTO() {
        //given
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO("Test", "4444");
        //when
        Customer customer = CustomerDTOMapper.dtoToEntityCreate(customerCreateDTO);
        //then
        assertEquals("4444", customer.getIdax());
        assertEquals("Test", customer.getName());
    }

    @Test
    void shouldReturnCustomerModelBuildFromCustomerIdOnlyDTO() {
        //given
        CustomerIdOnlyDTO customerIdOnlyDTO = new CustomerIdOnlyDTO(1L);
        //when
        Customer customer = CustomerDTOMapper.dtoToEntityIdOnly(customerIdOnlyDTO);
        //then
        assertNotNull(customer);
    }

    @Test
    void CustomerFieldsShouldBeSameAsGivenCustomerIdOnlyDTO() {
        //given
        CustomerIdOnlyDTO customerIdOnlyDTO = new CustomerIdOnlyDTO(1L);
        //when
        Customer customer = CustomerDTOMapper.dtoToEntityIdOnly(customerIdOnlyDTO);
        //then
        assertEquals(1L, customer.getId());
    }
}
