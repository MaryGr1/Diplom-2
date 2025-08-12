import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;
import org.junit.Before;
import org.junit.Test;

import static ch.qos.logback.classic.spi.CallerData.extract;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class CreatingAUserErrorTest extends BaseTest{

    private UserNoEmail userNoEmail;
    private UserNoPassword userNoPassword;
    private UserNoName userNoName;
    UserSteps userSteps;

    @Before

    public void setUp() {

        super.setUp();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Faker faker = new Faker();
        userNoEmail = new UserNoEmail();
        userNoEmail.setPassword(RandomStringUtils.randomAlphabetic(12));
        userNoEmail.setName(RandomStringUtils.randomAlphabetic(12));
        userNoPassword = new UserNoPassword();
        userNoPassword.setEmail(faker.internet().emailAddress());
        userNoPassword.setName(RandomStringUtils.randomAlphabetic(12));
        userNoName = new UserNoName();
        userNoName.setEmail(faker.internet().emailAddress());
        userNoName.setPassword(RandomStringUtils.randomAlphabetic(12));
        userSteps = new UserSteps(reqSpec);

    }

    // создание без логина

    @Test

    public void creationWithoutALoginNoEmailTest() {

        String actualErrorMessage = userSteps
                .createUserNoEmail(userNoEmail)
                .statusCode(403)
                .body("success", is(false))
                .extract()
                .path("message");
        String expectedErrorMessage = "Email, password and name are required fields";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    // создание без пароля

    @Test

    public void creationWithoutALoginNoPasswordTest() {

        String actualErrorMessage = userSteps
                .createUserNoPassword(userNoPassword)
                .statusCode(403)
                .body("success", is(false))
                .extract()
                .path("message");
        String expectedErrorMessage = "Email, password and name are required fields";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    // создание без имени

    @Test

    public void creationWithoutALoginNoNameTest() {

        String actualErrorMessage = userSteps
                .createUserNoName(userNoName)
                .statusCode(403)
                .body("success", is(false))
                .extract()
                .path("message");
        String expectedErrorMessage = "Email, password and name are required fields";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

}
