import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User;
import org.example.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;


public class CreatingAUserTest extends BaseTest {

    private User user;
    UserSteps userSteps;

    @Before

    public void setUp() {

        super.setUp();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Faker faker = new Faker();
        user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
        user.setName(RandomStringUtils.randomAlphabetic(12));
        userSteps = new UserSteps(reqSpec);

    }

    // успешная регистрация

    @Test

    public void creatingAUserTest() {

        userSteps
                .createUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    // регистрация с теми же данными

    @Test

    public void creatingAUserIdenticalStatusCodeTest() {

        userSteps
                .createUser(user);
        String actualErrorMessage =  userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .extract()
                .path("message");
        String expectedErrorMessage = "User already exists";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @After
    public void deleteUser() {
        try {
            userSteps.userDeleteAfterLogin(user);
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }
}



