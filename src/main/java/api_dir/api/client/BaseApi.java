package api_dir.api.client;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static constants.Constants.TBC_BASE_URI;

public class BaseApi {
    public static final RequestSpecification REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri(TBC_BASE_URI)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .addFilter(new AllureRestAssured())
            .build();
}
