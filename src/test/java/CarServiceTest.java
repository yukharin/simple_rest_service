import com.lanit.simple_rest_service.entities.Car;
import com.lanit.simple_rest_service.entities.Person;
import com.lanit.simple_rest_service.services.CarService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CarServiceTest {

    private static List<Car> cars;

    @BeforeAll
    public static void setUpAll() {
        cars = new ArrayList<>();
        LocalDate dateOfBirth = LocalDate.of(1994, 7, 21);
        Person person = new Person(1L, "ValidPerson", dateOfBirth);
        Car car = new Car(1L, "BMW-X5", 25, person);
        cars.add(car);
    }

    @AfterAll
    public static void tearDownAll() {
        cars = null;
    }

    @BeforeEach
    public static void setUp() {

    }

    @AfterEach
    public static void tearDown() {

    }

    @Test
    public void testCar() {
        CarService carService = Mockito.mock(CarService.class);
        Mockito.when(carService.cars()).thenReturn(cars);
    }
}

