package api_dir.api.client;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class MoneyTransferApi extends BaseApi {
    public Response getMoneyTransferOptions(String amount,String currencyCode, String receiveCountryCode){
        return given()
                .spec(REQUEST_SPECIFICATION)
                .queryParam("amount",amount)
                .queryParam("currencyCode",currencyCode)
                .queryParam("receiveCountryCode",receiveCountryCode)
                .contentType(ContentType.JSON)
                .when()
                .get(GET_M0NEY_TRANSFER_OPTIONS);
    }
    public Response getSystemInfo() {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .queryParam("locale","en-US")
                .contentType(ContentType.JSON)
                .when()
                .get(GET_SYSTEMS);
    }
}
