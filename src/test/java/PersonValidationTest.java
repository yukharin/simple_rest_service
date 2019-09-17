import com.lanit.simple_rest_service.entities.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonValidationTest {

    private static ValidatorFactory vf;
    private static Validator validator;


    @BeforeAll
    public static void setUp() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterAll
    public static void tearDown() {
        vf.close();
    }

    @Test
    public void testPersonConstraintViolation() {
        Person person = new Person(-1L, "ValidPerson", LocalDate.of(1994, 7, 21));
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(0, violations.size());
    }

    @Test
    public void testNullId() {
        Person person = new Person(null, "ValidPerson", LocalDate.of(1994, 7, 21));
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());
        assertEquals("Person id must not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{person.id.notnull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testNullName() {
        Person person = new Person(1L, null, LocalDate.of(1994, 7, 21));
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());
        assertEquals("Person name must not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{person.name.notnull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDateOfBirth() {
        Person person = new Person(-1L, "ValidPerson", LocalDate.of(3000, 7, 21));
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());
        assertEquals("Person date of birth should be in past time", violations.iterator().next().getMessage());
        assertEquals(LocalDate.of(3000, 7, 21), violations.iterator().next().getInvalidValue());
        assertEquals("{person.birthDate.past}", violations.iterator().next().getMessageTemplate());
    }


    @Test
    public void testNullDate() {
        Person person = new Person(1L, "ValidPerson", null);
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());
        assertEquals("Person date of birth must not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{person.birthDate.notnull}", violations.iterator().next().getMessageTemplate());
    }


}
