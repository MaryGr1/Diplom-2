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


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;


public class CreatingAnOrderTest {

    private Order order;
    OrderSteps orderSteps = new OrderSteps();
    UserSteps userSteps = new UserSteps();
    private User user;

    @Before

    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        order = new Order();
        user = new User();
    }

    // создание заказа с ингредиентами (незалогин)

    @Test

    public void creatingAnOrderTest() {
        order.setIngredients(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"});
        orderSteps
                .creatingAnOrder(order)
                .body("success", is(true));
    }

    // создание заказа без ингредиентов

    @Test

    public void creatingAnEmptyOrderTest(){
        order.setIngredients(new String[]{});
        orderSteps
                .creatingAnOrder(order)
                .statusCode(400)
                .body("success", is(false));
    }

    // создание с неверным хешем

    @Test

    public void creatingAnErrorOrderTest(){
        order.setIngredients(new String[]{RandomStringUtils.randomAlphabetic(24)});
        orderSteps
                .creatingAnOrder(order)
                .statusCode(500)
                .and()
                .assertThat().body("number", notNullValue());
    }

    // создание заказа с залогином

    @Test

    public void creatingAnOrderAuthTest(){
       userSteps
               .createUser(user);
       userSteps
               .loginUser(user);
       order.setIngredients(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"});
       orderSteps
               .creatingAnOrder(order)
               .body("success", is(true));
    }


}
