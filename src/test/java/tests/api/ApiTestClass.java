package tests.api;
import api_dir.steps.MoneyTransferSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import static constants.Constants.UNITED_STATES;
import static utils.DataGeneratorHelper.*;


@Test(groups = {"API Testing - Money Transfer - SCRUM-T4"})
public class ApiTestClass {
    MoneyTransferSteps moneyTransferSteps;
    List<String> expectedOptions, actualOptions;
    String currency, countryCode;
    int amount;
    @BeforeTest
    public void prepareData(){
        currency = getRandomCurrency();
        amount = getRandomAmount();
    }
    @BeforeClass
    public void setUp() {
        moneyTransferSteps = new MoneyTransferSteps();
        expectedOptions = moneyTransferSteps
                .getSystems()
                .getSystemsByCurrency(currency);
        countryCode = moneyTransferSteps
                .getCountries()
                .getCountryCodeByName(UNITED_STATES);
    }
    @Test(description = "")
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
