import com.lanit.simple_rest_service.entities.Car;
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

public class CarValidationTest {

    private static ValidatorFactory vf;
    private static Validator validator;
    private static Person mockPerson;


    @BeforeAll
    public static void setUp() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
        mockPerson = new Person(1L, "ValidPerson", LocalDate.of(1994, 7, 18));
    }

    @AfterAll
    public static void tearDown() {
        vf.close();
    }

    @Test
    public void testCarConstraintViolation() {
        Car car = new Car(1L, "BMW-X5", 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(0, violations.size());
    }

    @Test
    public void testNullId() {
        Car car = new Car(null, "Ferrari-430", 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Car id should not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{car.id.notnull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testNullModel() {
        Car car = new Car(1L, null, 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Car model should not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{car.model.notnull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testValidModelPattern() {
        Car car = new Car(1L, "La-da-vesta-granta", 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidModelPattern() {
        Car car = new Car(1L, "lada-", 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Model should match pattern ^([a-zA-Z0-9]+)([\\-])([a-zA-Z0-9\\-]+)$", violations.iterator().next().getMessage());
        assertEquals("lada-", violations.iterator().next().getInvalidValue());
        assertEquals("{car.model.pattern}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidModelPattern2() {
        Car car = new Car(1L, "-lada-granta", 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Model should match pattern ^([a-zA-Z0-9]+)([\\-])([a-zA-Z0-9\\-]+)$", violations.iterator().next().getMessage());
        assertEquals("-lada-granta", violations.iterator().next().getInvalidValue());
        assertEquals("{car.model.pattern}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidModelPattern3() {
        Car car = new Car(1L, "la$#da-granta", 25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Model should match pattern ^([a-zA-Z0-9]+)([\\-])([a-zA-Z0-9\\-]+)$", violations.iterator().next().getMessage());
        assertEquals("la$#da-granta", violations.iterator().next().getInvalidValue());
        assertEquals("{car.model.pattern}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testNullHorsePower() {
        Car car = new Car(1L, "BMW-X5", null, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Horsepower must not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{car.horsepower.notnull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testPositiveHorsePower() {
        Car car = new Car(1L, "BMW-X5", -25, mockPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Horsepower should be positive", violations.iterator().next().getMessage());
        assertEquals(-25, violations.iterator().next().getInvalidValue());
        assertEquals("{car.horsepower.positive}", violations.iterator().next().getMessageTemplate());
    }


    @Test
    public void testNullPerson() {
        Car car = new Car(1L, "BMW-X5", 25, null);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(2, violations.size());
        assertEquals("Owner field must not be null", violations.iterator().next().getMessage());
        assertEquals(null, violations.iterator().next().getInvalidValue());
        assertEquals("{car.owner.notnull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testAdultPerson() {
        LocalDate dateOfBirth = LocalDate.of(2019, 9, 13);
        Person youngPerson = new Person(1L, "Vlad", dateOfBirth);
        Car car = new Car(1L, "BMW-X5", 25, youngPerson);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertEquals(1, violations.size());
        assertEquals("Owner's age must be more or equals to 18", violations.iterator().next().getMessage());
        assertEquals(youngPerson, violations.iterator().next().getInvalidValue());
        assertEquals("{car.owner.adult}", violations.iterator().next().getMessageTemplate());
    }

}
