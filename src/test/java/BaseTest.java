import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

public class BaseTest {

    protected RequestSpecification reqSpec;

    @Before
    public void setUp() {
        reqSpec = RestAssured.given().baseUri("https://stellarburgers.nomoreparties.site");
    }
}