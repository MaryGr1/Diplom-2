import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreatingAUserErrorTest {

    private UserNoEmail userNoEmail;
    private UserNoPassword userNoPassword;
    private UserNoName userNoName;
    UserSteps userSteps = new UserSteps();

    @Before

    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
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

    }

    // создание без логина

    @Test

    public void creationWithoutALoginNoEmailTest() {

        userSteps
                .createUserNoEmail(userNoEmail)
                .statusCode(403)
                .body("success", is(false));
    }

    // создание без пароля

    @Test

    public void creationWithoutALoginNoPasswordTest() {

        userSteps
                .createUserNoPassword(userNoPassword)
                .statusCode(403)
                .body("success", is(false));
    }

    // создание без имени

    @Test

    public void creationWithoutALoginNoNameTest() {

        userSteps
                .createUserNoName(userNoName)
                .statusCode(403)
                .body("success", is(false));
    }

}
