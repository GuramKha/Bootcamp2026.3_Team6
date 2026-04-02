package tests.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import net.datafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui_dir.steps.CommonSteps;
import ui_dir.steps.HomeSteps;
import ui_dir.steps.MoneyTransfersSteps;

import static constants.Constants.*;

@Epic("Money Transfers")
@Feature("Remittance Conversion")
public class RemittanceConversionTest extends BaseTest {

    private HomeSteps homeSteps;
    private CommonSteps commonSteps;
    private MoneyTransfersSteps moneySteps;

    @BeforeClass
    public void setup() {
        homeSteps = new HomeSteps(page);
        commonSteps = new CommonSteps(page);
        moneySteps = new MoneyTransfersSteps(page);

        page.navigate(TBC_URL);
        homeSteps.acceptCookies();
    }

    @Test(priority = 1, description = "Navigate to Personal Products")
    @Severity(SeverityLevel.NORMAL)
    public void navigatePersonalProducts() {
        commonSteps
                .goToPersonal()
                .verifyPersonalProductsDisplayed();
    }

    @Test(priority = 2, description = "Choose Money Transfers")
    @Severity(SeverityLevel.CRITICAL)
    public void navigateMoneyTransfers() {

        commonSteps
                .goToMoneyTransfers();

        moneySteps
                .verifyMoneyTransfersPageOpened();
    }

    @Test(priority = 3, description = "Currency conversion from GBP to USD Remittance Conversion")
    @Severity(SeverityLevel.BLOCKER)
    public void conversionGBPToUSD() {
        moneySteps
                .enterAmount(faker.getRandomNumber(5000,10000))
                .openCurrencyInputDropdown()
                .selectCurrencyInput(GBP_STRING)
                .openCurrencyOutputDropdown()
                .selectCurrencyOutput(USD_STRING)
                .verifyConversion();
    }

    @Test(priority = 4, description = "Conversion of the increased amount from GBP to USD Remittance Conversion")
    @Severity(SeverityLevel.CRITICAL)
    public void conversionOfLargeAmount() {
        moneySteps
                .enterAmount(faker.getRandomNumber(15000,100000))
                .verifyConversion();
    }

    @Test(priority = 5, description = "Currency conversion for the same amount from USD to GBP Remittance Conversion")
    @Severity(SeverityLevel.CRITICAL)
    public void currencySwap() {
        moneySteps
                .swapCurrencies()
                .verifyConversion();
    }
}