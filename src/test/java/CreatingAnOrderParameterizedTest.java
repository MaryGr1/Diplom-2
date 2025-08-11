import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Order;
import org.example.OrderSteps;
import org.example.User;
import org.example.UserSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreatingAnOrderParameterizedTest {

    private Order order;
    private User user;
    UserSteps userSteps = new UserSteps();
    OrderSteps orderSteps = new OrderSteps();

    private String[] ingredients;
    private int statusCode;

    public CreatingAnOrderParameterizedTest(String[] ingredients, int statusCode) {
        this.ingredients = ingredients;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters

    public static Object[][] creatingAnOrder(){
            return new Object[][]{
                    {new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"}, 200},
                    {new String[]{RandomStringUtils.randomAlphabetic(24)}, 500},
                    {new String[]{}, 400}
                };
            }

    @Before

    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Faker faker = new Faker();
        order = new Order();
        user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
        user.setName(RandomStringUtils.randomAlphabetic(12));
        userSteps.createUser(user);
        userSteps.loginUser(user);
    }

    @Test

    public void creatingAnOrderTest(){
        order.setIngredients(ingredients);
        orderSteps
                .creatingAnOrder(order)
                .statusCode(statusCode);
    }


}
