import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.repositories.CarRepository;
import com.lanit.simple_rest_service.repositories.PersonRepository;
import com.lanit.simple_rest_service.services.CarService;
import org.junit.jupiter.api.*;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CarServiceTest {

    private static List<Car> cars;
    private static Car car;
    private static CarService carService;
    private static CarRepository carRepository;
    private static PersonRepository personRepository;
    private static Validator validator;


    @BeforeAll
    public static void setUpAll() {
        carRepository = mock(CarRepository.class);
        personRepository = mock(PersonRepository.class);
        validator = mock(javax.validation.Validator.class);
        carService = new CarService(carRepository, personRepository, validator);
    }

    @AfterAll
    public static void tearDownAll() {
        cars = null;
    }

    @BeforeEach
    public static void setUpEach() {
        cars = new ArrayList<>();
        LocalDate dateOfBirth = LocalDate.of(1994, 7, 21);
        Person person = new Person(1L, "ValidPerson", dateOfBirth);
        Car car = new Car(1L, "BMW-X5", 25, person);
        cars.add(car);
    }

    @AfterEach
    public static void tearDownEach() {

    }


    @Test
    public void testFindCarById() {
        when(carRepository.findByOwnerId(1L)).thenReturn(Optional.of())
        carService.findByOwnerId()
    }
}

