package api_dir.steps;
import api_dir.api.client.CountryApi;
import api_dir.api.client.MoneyTransferApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import test_data.models.response.GetCountriesResponse;
import test_data.models.response.GetMoneyTransferOptionsResponse;
import test_data.models.response.GetSystemsResponse;
import utils.WaitUtils;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MoneyTransferSteps {
    MoneyTransferApi moneyTransferApi = new MoneyTransferApi();
    CountryApi countryApi = new CountryApi();
    Response response;
    List<GetCountriesResponse> getCountriesResponse;
    List<GetMoneyTransferOptionsResponse> getMoneyTransferOptionsResponse;
    List<GetSystemsResponse> getSystemsResponse;
    @Step("Get money transfer options for amount: {0}, currency: {1}, country: {2}")
    public MoneyTransferSteps getMoneyTransferOptions(int amount, String currencyCode, String receiveCountryCode){
        response = WaitUtils.waitFor(
                () -> moneyTransferApi.getMoneyTransferOptions(String.valueOf(amount), currencyCode, receiveCountryCode),
                r -> r.getStatusCode() == 200 && !r.then().extract().body().jsonPath().getList("$").isEmpty(),
                10,
                500
        );
        getMoneyTransferOptionsResponse = Arrays.stream(response
                .then()
                .log().body()
                .body("size()", greaterThan(0))
                .assertThat()
                .statusCode(200)
                .extract().as(GetMoneyTransferOptionsResponse[].class)).toList();
        return this;
    }
    @Step("Get money transfer options for amount: {0}, currency: {1}, country: {2}")
    public MoneyTransferSteps getSystems() {
        getSystemsResponse =  Arrays.stream(moneyTransferApi.getSystemInfo().then().log().body().assertThat().statusCode(200).extract().as(GetSystemsResponse[].class)).toList();
        return this;
    }
    @Step("Extract money transfer system list from response")
    public List<String> getMoneyTransferSystemList(){
        return getMoneyTransferOptionsResponse.stream().map(
                GetMoneyTransferOptionsResponse::mtSystem
        ).toList();
    }
    @Step("Get systems that support currency: {0}")
    public List<String> getSystemsByCurrency(String currency) {
        return getSystemsResponse.stream()
                .filter(s -> s.currencies().contains(currency))
                .map(GetSystemsResponse::mtSystem)
                .toList();
    }
    @Step("Validate that actual systems are supported by expected systems")
    public MoneyTransferSteps validateSystemsSupportCurrency(List<String> expectedOptions, List<String> actualOptions) {
        actualOptions.forEach(
                option -> assertThat(expectedOptions,hasItem(option)
                ));
        return this;
    }
    @Step("Validate that returned options size is less or equal to expected systems")
    public MoneyTransferSteps validateOptionsSizeByCurrency(List<String> expectedSystems) {
        assertThat("Returned list size should be less or equal to expected systems",
                getMoneyTransferOptionsResponse.size() <= expectedSystems.size());
        return this;
    }
    @Step("Validate that each money transfer option has mtSystem value")
    public MoneyTransferSteps validateMtSystemValue() {
        getMoneyTransferOptionsResponse.forEach(
                option -> assertThat(option.mtSystem(), notNullValue())
        );
        return this;
    }
    @Step("Validate that each money transfer option has fee value")
    public MoneyTransferSteps validateFeeValue() {
        getMoneyTransferOptionsResponse.forEach(
                option -> assertThat(option.fee(), notNullValue())
        );
        return this;
    }
    @Step("Get list of countries with codes")
    public MoneyTransferSteps getCountries(){
        getCountriesResponse = Arrays.stream(countryApi.getCountryCode().then().log().body().assertThat().statusCode(200).extract().as(GetCountriesResponse[].class)).toList();
        return this;
    }
    @Step("Get country code by country name: {0}")
    public String getCountryCodeByName(String countryName){
        return getCountriesResponse.stream()
                .filter(c -> c.name().equalsIgnoreCase(countryName))
                .map(GetCountriesResponse::code)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Country not found: " + countryName));
    }

}
