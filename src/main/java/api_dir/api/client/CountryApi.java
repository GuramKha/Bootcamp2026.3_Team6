package api_dir.api.client;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static constants.Constants.GET_COUNTRIES;
import static io.restassured.RestAssured.given;

public class CountryApi extends BaseApi {
    public Response getCountryCode(){
        return given()
                .spec(REQUEST_SPECIFICATION)
                .queryParam("locale","en-US")
                .contentType(ContentType.JSON)
                .when()
                .get(GET_COUNTRIES);
    }
}
