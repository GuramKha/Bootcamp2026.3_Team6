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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MoneyTransferSteps {
    public  MoneyTransferApi moneyTransferApi = new MoneyTransferApi();
    public CountryApi countryApi = new CountryApi();
    public static final Logger logger = LogManager.getLogger(MoneyTransferSteps.class);

    public Response moneyTransferResponse;
    public List<GetCountriesResponse> getCountriesResponse;
    public List<GetMoneyTransferOptionsResponse> getMoneyTransferOptionsResponse;
    public List<GetSystemsResponse> getSystemsResponse;

    @Step("Get money transfer options for amount: {0}, currency: {1}, country: {2}")
    public MoneyTransferSteps getMoneyTransferOptions(int amount, String currencyCode, String receiveCountryCode){
        logger.info("Calling MoneyTransfer API for amount: {}, currency: {}, country: {}", amount, currencyCode, receiveCountryCode);

        moneyTransferResponse = WaitUtils.waitFor(
                () -> moneyTransferApi.getMoneyTransferOptions(
                        String.valueOf(amount), currencyCode, receiveCountryCode),
                r -> {
                    String body = r.getBody().asString();
                    return r.getStatusCode() == 200
                            && body != null
                            && !body.isBlank()
                            && !body.equals("[]");
                },
                10,
                500
        );
        getMoneyTransferOptionsResponse = Arrays.stream(moneyTransferResponse
                .then()
                .log().body()
                .assertThat()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract().as(GetMoneyTransferOptionsResponse[].class)).toList();


        return this;
    }

    @Step("Get money transfer options for amount: {0}, currency: {1}, country: {2}")
    public MoneyTransferSteps getSystems() {
        logger.info("Fetching all systems info.");

        getSystemsResponse =  Arrays.stream(moneyTransferApi.getSystemInfo()
                .then().log().body()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(GetSystemsResponse[].class))
                .toList();

        logger.debug("Systems info fetched: {}", getSystemsResponse);


        return this;
    }

    @Step("Extract money transfer system list from response")
    public List<String> getMoneyTransferSystemList(){
        logger.info("Extracting money transfer system list from response.");

        List<String> systems = getMoneyTransferOptionsResponse.stream()
                .map(GetMoneyTransferOptionsResponse::mtSystem)
                .toList();

        logger.debug("Extracted systems: {}", systems);


        return systems;
    }

    @Step("Get systems that support currency: {0}")
    public List<String> getSystemsByCurrency(String currency) {
        logger.info("Filtering systems that support currency: {}", currency);

        List<String> systems = getSystemsResponse.stream()
                .filter(s -> s.currencies().contains(currency))
                .map(GetSystemsResponse::mtSystem)
                .toList();

        logger.debug("Filtered systems: {}", systems);


        return systems;

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
                option -> assertThat(option.mtSystem(), matchesPattern("^[A-Za-z ]+$"))
        );


        return this;
    }

    @Step("Validate that each money transfer option has fee value")
    public MoneyTransferSteps validateFeeValue() {
        getMoneyTransferOptionsResponse.forEach(
                option -> assertThat(option.fee(),  greaterThanOrEqualTo(0))
        );


        return this;
    }

    @Step("Get list of countries with codes")
    public MoneyTransferSteps getCountries(){
        logger.info("Fetching list of countries with codes.");

        getCountriesResponse = Arrays.stream(countryApi.getCountryCode()
                .then()
                .log().body().
                assertThat()
                .statusCode(200)
                .extract()
                .as(GetCountriesResponse[].class))
                .toList();

        logger.debug("Fetched countries: {}", getCountriesResponse);


        return this;
    }

    @Step("Get country code by country name: {0}")
    public String getCountryCodeByName(String countryName){
        logger.info("Getting country code for country: {}", countryName);

        return getCountriesResponse.stream()
                .filter(c -> c.name().equalsIgnoreCase(countryName))
                .map(GetCountriesResponse::code)
                .findFirst()
                .orElseThrow(() ->{
                    logger.error("Country not found: {}", countryName);
                    return new RuntimeException("Country not found: " + countryName);
                });
    }

}
