package tests.api;
import api_dir.steps.MoneyTransferSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.FakerSingleton;
import java.util.List;
import static constants.Constants.UNITED_STATES;


@Feature("Money Transfer")
@Test(groups = {"E2E - Money Transfer - API Testing - SCRUM-T4"})
public class MoneyTransferApiScenarioTest {
    public FakerSingleton faker;
    public MoneyTransferSteps moneyTransferSteps;

    public List<String> expectedOptions, actualOptions;
    public String currency, countryCode;
    public int amount;

    @BeforeTest
    public void prepareData(){
        faker = FakerSingleton.getInstance();
        moneyTransferSteps = new MoneyTransferSteps();

        currency = faker.getRandomCurrency();
        amount = faker.getRandomAmount();
    }

    @BeforeClass
    public void setUp() {
        expectedOptions = moneyTransferSteps
                .getSystems()
                .getSystemsByCurrency(currency);

        countryCode = moneyTransferSteps
                .getCountries()
                .getCountryCodeByName(UNITED_STATES);
    }

    @Test(description = "Checking the Remittance Fee Calculation to USA")
    @Severity(SeverityLevel.CRITICAL)
    public void getMoneyTransferOptionsTest() {
        actualOptions = moneyTransferSteps
                            .getMoneyTransferOptions(amount,currency,countryCode)
                            .getMoneyTransferSystemList();

        moneyTransferSteps
                .validateMtSystemValue()
                .validateFeeValue()
                .validateSystemsSupportCurrency(expectedOptions, actualOptions)
                .validateOptionsSizeByCurrency(expectedOptions);
    }
}
