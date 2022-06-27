package com.adaxlab.jmix.rys.entity;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    SystemAuthenticator systemAuthenticator;
    @Autowired
    Validator validator;
    private Customer customer;


    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

        //given

        customer.setFirstName("");
        customer.setLastName("");
        customer.setEmailAddress("");

        Address address = dataManager.create(Address.class);

        address.setStreet("");
        address.setCity("");
        address.setPostCode("");

        customer.setAddress(address);

        //when
        Customer savedCustomer = systemAuthenticator.withSystem(() -> dataManager.save(customer));

        //then
        assertThat(savedCustomer.getId())
                .isNotNull();


    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalid() {

        //given
        customer.setEmailAddress("invalidEmailAddress");

        //when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer, Default.class);

        //then
        assertThat(violations)
                .hasSize(1);
        //and

        assertThat(firstViolation(violations).getPropertyPath().toString())
                .isEqualTo("emailAddress");
        assertThat(firstViolation(violations).getMessageTemplate())
                .isEqualTo("{javax.validation.constraints.Email.message}");
    }

    private ConstraintViolation<Customer> firstViolation(Set<ConstraintViolation<Customer>> violations) {
        return violations.stream().findFirst().orElseThrow();
    }
}
