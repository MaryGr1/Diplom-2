import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Order;
import org.example.OrderSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreatingAnOrderParameterizedTest {

    private Order order;
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
        order = new Order();
    }

    @Test

    public void creatingAnOrderTest(){
        order.setIngredients(ingredients);
        orderSteps
                .creatingAnOrder(order)
                .statusCode(statusCode);
    }


}
