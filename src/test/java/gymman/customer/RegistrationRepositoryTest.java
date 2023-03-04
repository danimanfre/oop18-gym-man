package gymman.customer;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import gymman.common.DuplicateEntityException;
import gymman.customers.Customer;
import gymman.customers.InvalidValueException;
import gymman.customers.NumberedRegistration;
import gymman.customers.RegistrationRepository;
import gymman.customers.RegistrationRepositoryImpl;
import gymman.customers.SubscriptionType;
import gymman.customers.TermRegistration;

public class RegistrationRepositoryTest {

    private RegistrationRepository repo;


    @Before
    public void setUp() throws Exception {
        this.repo = new RegistrationRepositoryImpl();
    }

    @Test
    public void testCanAddTermRegistration() throws InvalidValueException {

        Customer customer = Customer.builder()
                .firstname("Mario")
                .lastname("Rossi")
                .username("mRed")
                .fiscalCode("RSSMRA80A01H199L")
                .birthdate(LocalDate.of(1980, 1, 1))
                .email("mariorossi@gmail.com")
                .telephoneNumber("3312468247")
                .build();
        SubscriptionType subscription = SubscriptionType.builder().name("sala pesi").description("tirare su pesi").unitPrice(10).build();
        TermRegistration registration = TermRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .signingDate(LocalDate.of(2030, 07, 10))
                .duration(4)
                .discount(10)
                .additionalService(null).build();
        try {
            this.repo.add(registration);
        } catch (DuplicateEntityException e) {
            fail();
        }

        assertThat(this.repo.contains(registration), is(true));
    }

    @Test
    public void testCanAddNumberedRegistration() throws InvalidValueException {

        Customer customer = Customer.builder()
                .firstname("Mario")
                .lastname("Rossi")
                .username("mRed")
                .fiscalCode("RSSMRA80A01H199L")
                .birthdate(LocalDate.of(1980, 1, 1))
                .email("mariorossi@gmail.com")
                .telephoneNumber("3312468247")
                .build();
        SubscriptionType subscription = SubscriptionType.builder().name("sala pesi").description("tirare su pesi").unitPrice(10).build();
        NumberedRegistration registration = NumberedRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .maxEntries(20)
                .discount(10)
                .additionalService(null).build();
        try {
            this.repo.add(registration);
        } catch (DuplicateEntityException e) {
            fail();
        }

        assertThat(this.repo.contains(registration), is(true));
    }

    @Test
    public void testCanAddRegistrationWithDifferentSubscriptionInTheSamePeriod() throws InvalidValueException {

        Customer customer = Customer.builder()
                .firstname("Mario")
                .lastname("Rossi")
                .username("mRed")
                .fiscalCode("RSSMRA80A01H199L")
                .birthdate(LocalDate.of(1980, 1, 1))
                .email("mariorossi@gmail.com")
                .telephoneNumber("3312468247")
                .build();

        SubscriptionType subscriptionA = SubscriptionType.builder().name("sala pesi").description("tirare su pesi").unitPrice(10).build();
        SubscriptionType subscriptionB = SubscriptionType.builder().name("zumba").description("balla e salta").unitPrice(8).build();

        TermRegistration registrationA = TermRegistration.builder()
                .idClient(customer.getId())
                .type(subscriptionA)
                .signingDate(LocalDate.of(2021, 7, 10))
                .duration(4)
                .discount(10)
                .additionalService(null).build();

        TermRegistration registrationB = TermRegistration.builder()
                .idClient(customer.getId())
                .type(subscriptionB)
                .signingDate(LocalDate.of(2021, 7, 10))
                .duration(4)
                .discount(10)
                .additionalService(null).build();

        try {
            this.repo.add(registrationA);

            this.repo.add(registrationB);

        } catch (DuplicateEntityException e) {
            fail();
        }
        assertThat(this.repo.contains(registrationB), is(true));
    }


    @Test(expected = DuplicateEntityException.class)
    public void testPreventAddingRegistrationWithSameSubscriptionTypeInTheSamePeriod() throws DuplicateEntityException {

        Customer customer = Customer.builder()
                .firstname("Mario")
                .lastname("Rossi")
                .username("mRed")
                .fiscalCode("RSSMRA80A01H199L")
                .birthdate(LocalDate.of(1980, 1, 1))
                .email("mariorossi@gmail.com")
                .telephoneNumber("3312468247")
                .build();
        SubscriptionType subscription = SubscriptionType.builder().name("sala pesi").description("tirare su pesi").unitPrice(10).build();
        TermRegistration registrationA = TermRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .signingDate(LocalDate.of(2023, 1, 1))
                .duration(4)
                .discount(10)
                .additionalService(null).build();
        TermRegistration registrationB = TermRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .signingDate(LocalDate.of(2023, 2, 2))
                .duration(4)
                .discount(10)
                .additionalService(null).build();
        this.repo.add(registrationA);
        this.repo.add(registrationB);
    }

    @Test (expected = InvalidValueException.class)
    public void testPreventAddRegistrationWithSigningDatePriorToTheCurrentDay() throws InvalidValueException, DuplicateEntityException {

        Customer customer = Customer.builder()
                .firstname("Mario")
                .lastname("Rossi")
                .username("mRed")
                .fiscalCode("RSSMRA80A01H199L")
                .birthdate(LocalDate.of(1980, 1, 1))
                .email("mariorossi@gmail.com")
                .telephoneNumber("3312468247")
                .build();
        SubscriptionType subscription = SubscriptionType.builder().name("sala pesi").description("tirare su pesi").unitPrice(10).build();
        TermRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .signingDate(LocalDate.of(2000, 1, 1))
                .duration(4)
                .discount(10)
                .additionalService(null).build();
    }

    @Test(expected = DuplicateEntityException.class)
    public void testPreventAddingNumberedRegistrationWithSameSubscriptionType() throws DuplicateEntityException {
        Customer customer = Customer.builder()
                .firstname("Mario")
                .lastname("Rossi")
                .username("mRed")
                .fiscalCode("RSSMRA80A01H199L")
                .birthdate(LocalDate.of(1980, 1, 1))
                .email("mariorossi@gmail.com")
                .telephoneNumber("3312468247")
                .build();
        SubscriptionType subscription = SubscriptionType.builder().name("sala pesi").description("tirare su pesi").unitPrice(10).build();
        NumberedRegistration registrationA = NumberedRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .maxEntries(20)
                .discount(0)
                .additionalService(null)
                .build();
        NumberedRegistration registrationB = NumberedRegistration.builder()
                .idClient(customer.getId())
                .type(subscription)
                .maxEntries(30)
                .discount(50)
                .additionalService(null)
                .build();
        this.repo.add(registrationA);
        this.repo.add(registrationB);
    }
}
