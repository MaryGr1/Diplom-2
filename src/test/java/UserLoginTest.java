import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User;
import org.example.UserSteps;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class UserLoginTest {

    private User user;
    UserSteps userSteps = new UserSteps();

    @Before

    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Faker faker = new Faker();
        user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
        user.setName(RandomStringUtils.randomAlphabetic(12));
        userSteps
                .createUser(user);

    }

    // логин существующим юзером

    @Test

    public void userLoginSuccessTest() {

       userSteps
               .loginUser(user)
               .body("success", is(true));
    }

    // логин с несуществующим email

    @Test

    public void userLoginFakeEmailTest() {

        User fakeUser = new User();
        fakeUser.setEmail("nonExistentLogin@email.com");
        fakeUser.setPassword(user.getPassword());
        userSteps
                .loginUser(fakeUser)
                .statusCode(401)
                .body("success", is(false));
    }

    // логин с несуществующим паролем

    @Test

    public void userLoginFakePasswordTest() {

        User fakeUser = new User();
        fakeUser.setEmail(user.getEmail());
        fakeUser.setPassword("nonExistentPassword");
        userSteps
                .loginUser(fakeUser)
                .statusCode(401)
                .body("success", is(false));
    }


}
