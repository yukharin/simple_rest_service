//import com.lanit.simple_rest_service.entities.Car;
//import com.lanit.simple_rest_service.entities.Person;
//import com.lanit.simple_rest_service.repositories.CarRepository;
//import com.lanit.simple_rest_service.repositories.PersonRepository;
//import com.lanit.simple_rest_service.services.CarService;
//import org.junit.jupiter.api.*;
//
//import javax.validation.Validator;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
//public class CarServiceTest {
//
//
//    private  Car car;
//    private CarService carService;
//    private CarRepository carRepository;
//    private  PersonRepository personRepository;
//    private  Validator validator;
//
//
//    @BeforeAll
//    public void setUpAll() {
//        carRepository = mock(CarRepository.class);
//        personRepository = mock(PersonRepository.class);
//        validator = mock(javax.validation.Validator.class);
//        carService = new CarService(carRepository, personRepository, validator);
//    }
//
//    @AfterAll
//    public void tearDownAll() {
//
//    }
//
//    @BeforeEach
//    public void setUpEach() {
////        cars = new ArrayList<>();
////        LocalDate dateOfBirth = LocalDate.of(1994, 7, 21);
////        Person person = new Person(1L, "ValidPerson", dateOfBirth);
////        Car car = new Car(1L, "BMW-X5", 25, person);
////        cars.add(car);
//    }
//
//    @AfterEach
//    public void tearDownEach() {
//
//    }
//
//
//    @Test
//    public void testFindCarById() {
//        when(carRepository.findByOwnerId(1L)).
//    }
//}

