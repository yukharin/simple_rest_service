import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:webAppContext.xml"})
@ActiveProfiles("test")
public class EndPointTest {


    private static final String CLEAR_URL = "/clear";
    private static final String CAR_URL = "/car";
    private static final String PERSON_URL = "/person";
    private static final String PERSON_WITH_CARS_URL = "/personwithcars";
    private static final String STATISTICS_URL = "/statistics";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Order(value = 0)
    @Test
    void testClear() throws Exception {

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isNotFound());
    }

    @Test
    void addValidPerson() throws Exception {

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addValidPersonWithYoungAge() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2015\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2015"));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addValidPersonEmptyName() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"\",\"birthdate\":\"01.01.2015\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(""))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2015"));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonFutureBirthDate() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"BadPerson\",\"birthdate\":\"01.01.3000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addBadPersonEmptyId() throws Exception {
        String jsonPerson = "{\"id\":\"\",\"name\":\"BadPerson\",\"birthdate\":\"01.01.3000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonNullId() throws Exception {
        String jsonPerson = "{\"name\":\"BadPerson\",\"birthdate\":\"01.01.3000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonNullName() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"birthdate\":\"01.01.3000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonNullBirthdate() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"BadPerson\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addBadPersonIncorrectBirthdate() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"BadPerson\",\"birthdate\":\"01-01-2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonLanientBirthdate() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"BadPerson\",\"birthdate\":\"01.15.2017\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonSymbolsBirthdate() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"BadPerson\",\"birthdate\":\"symbols\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadPersonUniques() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void getPersonWithEmptyId() throws Exception {
        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", ""))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getPersonWithSymbolsId() throws Exception {
        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "symbols"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void addValidCar() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonCar = "{\"id\":\"-130\",\"model\":\"BMW-X7\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonCar)).andExpect(status().isOk());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(1)))
                .andExpect(jsonPath("$.cars[?(@.model=='BMW-X7')].horsepower").value(100));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadCarUnique() throws Exception {

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonCar = "{\"id\":\"-130\",\"model\":\"BMW-X7\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonCar)).andExpect(status().isOk());

        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonCar)).andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addValidCars() throws Exception {

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"BMW-X7\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isOk());

        String secondCar = "{\"id\":\"-131\",\"model\":\"BMW-X8\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(secondCar))
                .andExpect(status().isOk());

        String thirdCar = "{\"id\":\"-132\",\"model\":\"BMW-X9\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(thirdCar))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(3)))
                .andExpect(jsonPath("$.cars[?(@.model=='BMW-X7')].horsepower").value(100))
                .andExpect(jsonPath("$.cars[?(@.model=='BMW-X7')].id").value(-130))
                .andExpect(jsonPath("$.cars[?(@.model=='BMW-X8')].id").value(-131))
                .andExpect(jsonPath("$.cars[?(@.model=='BMW-X9')].id").value(-132));


        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addValidCarsModelFormatVariations() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"La-da-Devyatka\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isOk());

        String secondCar = "{\"id\":\"-131\",\"model\":\"La-da-\",\"horsepower\":200,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(secondCar))
                .andExpect(status().isOk());


        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(2)))
                .andExpect(jsonPath("$.cars[?(@.model=='La-da-Devyatka')].id").value(-130))
                .andExpect(jsonPath("$.cars[?(@.model=='La-da-')].id").value(-131))
                .andExpect(jsonPath("$.cars[?(@.model=='La-da-Devyatka')].horsepower").value(100))
                .andExpect(jsonPath("$.cars[?(@.model=='La-da-')].horsepower").value(200));


        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addBadCarModelFormat() throws Exception {

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"-da-Devyatka\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(0)));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadCarNegativeHorsepower() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"horsepower\":-100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(0)));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadCarWithYoungPerson() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2019\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2019"))
                .andExpect(jsonPath("$.cars", hasSize(0)));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void addBadCarEmptyNullModel() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        String jsonSecondCar = "{\"id\":\"-130\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(0)));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addBadCarEmptyNullId() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"\",\"model\":\"BMW-X5\",\"horsepower\":-100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        String jsonSecondCar = "{\"model\":\"BMW-X5\",\"horsepower\":-100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(0)));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addBadCarEmptyNullZeroHorsePower() throws Exception {
        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"horsepower\": \"\",\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        String jsonSecondCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"horsepower\":0 ,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isBadRequest());

        String jsonThirdCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonThirdCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(PERSON_WITH_CARS_URL)
                .param("personid", "-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.id").value("-100"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(jsonPath("$.cars", hasSize(0)));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void addBadCarEmptyOwnerId() throws Exception {

        String jsonFirstCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"horsepower\":100,\"ownerId\":\"\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isBadRequest());

        String jsonSecondCar = "{\"id\":\"-130\",\"model\":\"BMW-X5\",\"horsepower\":100}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void getStatistics() throws Exception {
        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.personcount").value(0))
                .andExpect(jsonPath("$.carcount").value(0))
                .andExpect(jsonPath("$.uniquevendorcount").value(0));

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-230\",\"model\":\"BMW-X5\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isOk());

        String jsonSecondCar = "{\"id\":\"-229\",\"model\":\"BMW-X3\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isOk());

        String jsonThirdCar = "{\"id\":\"-228\",\"model\":\"Lada-Devyatka\",\"horsepower\":50,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonThirdCar))
                .andExpect(status().isOk());

        String jsonForthCar = "{\"id\":\"-227\",\"model\":\"La-da-Devyatka\",\"horsepower\":50,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post((CAR_URL))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonForthCar))
                .andExpect(status().isOk());

        String jsonFifthCar = "{\"id\":\"-226\",\"model\":\"La-da-\",\"horsepower\":50,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFifthCar))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.personcount").value(1))
                .andExpect(jsonPath("$.carcount").value(5))
                .andExpect(jsonPath("$.uniquevendorcount").value(3));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


    @Test
    void getStatisticsTwoNotAdd() throws Exception {
        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.personcount").value(0))
                .andExpect(jsonPath("$.carcount").value(0))
                .andExpect(jsonPath("$.uniquevendorcount").value(0));

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonBadPerson = "{\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonBadPerson))
                .andExpect(status().isBadRequest());


        String jsonFirstCar = "{\"id\":\"-230\",\"model\":\"BMW-X5\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isOk());

        String jsonSecondCar = "{\"model\":\"BMW-X3\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.personcount").value(1))
                .andExpect(jsonPath("$.carcount").value(1))
                .andExpect(jsonPath("$.uniquevendorcount").value(1));

        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }

    @Test
    void getStatisticsIgnoreCase() throws Exception {
        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.personcount").value(0))
                .andExpect(jsonPath("$.carcount").value(0))
                .andExpect(jsonPath("$.uniquevendorcount").value(0));

        String jsonPerson = "{\"id\":\"-100\",\"name\":\"Validperson1\",\"birthdate\":\"01.01.2000\"}";
        this.mockMvc.perform(post(PERSON_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk());

        String jsonFirstCar = "{\"id\":\"-230\",\"model\":\"BMW-X5\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFirstCar))
                .andExpect(status().isOk());

        String jsonSecondCar = "{\"id\":\"-229\",\"model\":\"BmW-X3\",\"horsepower\":100,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonSecondCar))
                .andExpect(status().isOk());

        String jsonThirdCar = "{\"id\":\"-228\",\"model\":\"Lada-devyatka\",\"horsepower\":50,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonThirdCar))
                .andExpect(status().isOk());

        String jsonForthCar = "{\"id\":\"-227\",\"model\":\"La-da-Devyatka\",\"horsepower\":50,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonForthCar))
                .andExpect(status().isOk());

        String jsonFifthCar = "{\"id\":\"-226\",\"model\":\"La-da-\",\"horsepower\":50,\"ownerId\":\"-100\"}";
        this.mockMvc.perform(post(CAR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonFifthCar))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(STATISTICS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.personcount").value(1))
                .andExpect(jsonPath("$.carcount").value(5))
                .andExpect(jsonPath("$.uniquevendorcount").value(3));


        this.mockMvc.perform(get(CLEAR_URL))
                .andExpect(status().isOk());
    }


}
