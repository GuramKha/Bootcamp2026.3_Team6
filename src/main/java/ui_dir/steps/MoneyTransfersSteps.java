package ui_dir.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui_dir.pages.MoneyTransfersPage;
import utils.RateUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.Constants.ERROR;
import static constants.Constants.MONEY_TRANSFERS_URL;
import static org.testng.AssertJUnit.assertTrue;

public class MoneyTransfersSteps {

    private static final Logger logger = LogManager.getLogger(MoneyTransfersSteps.class);

    private final MoneyTransfersPage moneyTransfersPage;
    private final Page page;

    public MoneyTransfersSteps(Page page) {
        this.moneyTransfersPage = new MoneyTransfersPage(page);
        this.page = page;
    }

    @Step("Verify Money Transfers page opened")
    public MoneyTransfersSteps verifyMoneyTransfersPageOpened() {
        logger.debug("Expected URL: " + MONEY_TRANSFERS_URL);

        assertThat(page).hasURL(MONEY_TRANSFERS_URL);
        assertThat(moneyTransfersPage.navbarMoneyTransfers).isVisible();

        return this;
    }

    @Step("Enter amount: {amount}")
    public MoneyTransfersSteps enterAmount(String amount) {
        logger.info("Entering amount: " + amount);

        moneyTransfersPage.moneyInput.first()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.moneyInput.first().fill(amount);

        return this;
    }

    @Step("Select input currency: {currency}")
    public MoneyTransfersSteps selectCurrencyInput(String currency) {
        logger.info("Selecting input currency: " + currency);

        moneyTransfersPage.currencyItem(currency).click();

        return this;
    }

    @Step("Open currency input dropdown")
    public MoneyTransfersSteps openCurrencyInputDropdown() {
        page.evaluate("window.scrollBy(0, 600)");

        moneyTransfersPage.currencyDropDownInput
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyDropDownInput.click();

        return this;
    }

    @Step("Select output currency: {currency}")
    public MoneyTransfersSteps selectCurrencyOutput(String currency) {
        logger.info("Selecting output currency: " + currency);

        moneyTransfersPage.currencyItem(currency).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyItem(currency).click(new Locator.ClickOptions().setForce(true));

        return this;
    }

    @Step("Open currency output dropdown")
    public MoneyTransfersSteps openCurrencyOutputDropdown() {
        moneyTransfersPage.currencyDropDownOutput.first()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyDropDownOutput.click(new Locator.ClickOptions().setForce(true));

        return this;
    }

    @Step("Verify conversion")
    public MoneyTransfersSteps verifyConversion() {
        double rate = RateUtils.extractRate(
                moneyTransfersPage.convertionRate.innerText()
        );

        double amount = RateUtils.parseAmount(
                moneyTransfersPage.moneyInput.first().inputValue()
        );

        double actual = RateUtils.parseAmount(
                moneyTransfersPage.moneyInput.last().inputValue()
        );

        double expected = RateUtils.calculateExpected(amount, rate);
        double roundedExpected = RateUtils.round(expected);

        logger.debug("rate=" + rate +
                ", amount=" + amount +
                ", actual=" + actual +
                ", expected=" + roundedExpected);

        assertTrue(RateUtils.isCloseEnough(actual, roundedExpected));

        return this;
    }

    @Step("Swap currencies")
    public MoneyTransfersSteps swapCurrencies() {
        logger.info("Swapping currencies");

        moneyTransfersPage.swapButton.click();

        return this;
    }

    @Step("Open remittance fee calculation")
    public MoneyTransfersSteps openRemittanceFeeCalculation() {
        moneyTransfersPage.remittanceFeeCalculation.click();

        return this;
    }

    @Step("Select country Greece")
    public MoneyTransfersSteps selectCountry(String country) {
        logger.info("Selecting country: " + country);

        moneyTransfersPage.countryItem(country).click();

        return this;
    }

    @Step("Open country dropdown")
    public MoneyTransfersSteps openCountryDropdown() {
        moneyTransfersPage.countryDropDown
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.countryDropDown.click();

        return this;
    }

    @Step("Verify providers displayed")
    public MoneyTransfersSteps verifyProvidersDisplayed() {
        moneyTransfersPage.cards.first()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        int count = moneyTransfersPage.cards.count();
        logger.debug("Providers count: " + count);

        assertTrue(count > 0);

        return this;
    }

    @Step("Enter invalid amount: {value}")
    public MoneyTransfersSteps enterInvalidAmount(String value) {
        logger.warn("Entering invalid amount: " + value);

        moneyTransfersPage.moneyInput.first().fill(value);

        return this;
    }

    @Step("Verify error message")
    public MoneyTransfersSteps verifyErrorMessage() {
        logger.debug("Expected error: " + ERROR);

        assertThat(moneyTransfersPage.errorMessage).containsText(ERROR);

        return this;
    }
}