import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Order;
import org.example.OrderSteps;
import org.example.User;
import org.example.UserSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreatingAnOrderParameterizedTest extends BaseTest{

    private Order order;
    private User user;
    UserSteps userSteps;
    OrderSteps orderSteps;

    private String[] ingredients;
    private int statusCode;
    private String expectedErrorMessage;

    public CreatingAnOrderParameterizedTest(String[] ingredients, int statusCode, String expectedErrorMessage) {
        this.ingredients = ingredients;
        this.statusCode = statusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters

    public static Object[][] creatingAnOrder(){
            return new Object[][]{
                    {new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"}, 200, null},
                    {new String[]{RandomStringUtils.randomAlphabetic(24)}, 500, null},
                    {new String[]{}, 400, "Ingredient ids must be provided"}
                };
            }

    @Before

    public void setUp(){

        super.setUp();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Faker faker = new Faker();
        order = new Order();
        user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
        user.setName(RandomStringUtils.randomAlphabetic(12));
        userSteps = new UserSteps(reqSpec);
        orderSteps = new OrderSteps(reqSpec);
        userSteps.createUser(user);
        userSteps.loginUser(user);
    }

    @Test

    public void creatingAnOrderTest(){
        order.setIngredients(ingredients);
        ValidatableResponse response = orderSteps
                .creatingAnOrder(order)
                .statusCode(statusCode);

        if (statusCode == 400) {
            //Убедись, что orderSteps.creatingAnOrder() возвращает JSON, а не XML
            String actualMessage = response.extract().path("message"); //Извлечение значения по JSON пути
            assertEquals(expectedErrorMessage, actualMessage);
        }
    }


}
