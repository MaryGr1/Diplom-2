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

public class UserLoginTest extends BaseTest{

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
        String actualErrorMessage =  userSteps
                .loginUser(fakeUser)
                .statusCode(401)
                .body("success", is(false))
                .extract()
                .path("message");
        String expectedErrorMessage = "email or password are incorrect";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    // логин с несуществующим паролем

    @Test

    public void userLoginFakePasswordTest() {

        User fakeUser = new User();
        fakeUser.setEmail(user.getEmail());
        fakeUser.setPassword("nonExistentPassword");
        String actualErrorMessage =  userSteps
                .loginUser(fakeUser)
                .statusCode(401)
                .body("success", is(false))
                .extract()
                .path("message");
        String expectedErrorMessage = "email or password are incorrect";
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
