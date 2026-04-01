package ui_dir.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import ui_dir.pages.MoneyTransfersPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.Constants.ERROR;
import static org.testng.AssertJUnit.assertTrue;

public class MoneyTransfersSteps {

    private final MoneyTransfersPage moneyTransfersPage;

    public MoneyTransfersSteps(Page playwrightPage) {
        this.moneyTransfersPage = new MoneyTransfersPage(playwrightPage);
    }

    @Step("Verify Money Transfers page opened")
    public MoneyTransfersSteps verifyMoneyTransfersPageOpened() {
        assertThat(moneyTransfersPage.navbarMoneyTransfers).isVisible();

        return this;
    }

    @Step("Enter amount: {amount}")
    public MoneyTransfersSteps enterAmount(String amount) {
        moneyTransfersPage.moneyInput.first().fill(amount);

        return this;
    }

    @Step("Select input currency: {currency}")
    public MoneyTransfersSteps selectCurrencyInput(String currency) {
        moneyTransfersPage.currencyDropDownInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyDropDownInput.click();
        moneyTransfersPage.currencyItem.filter(new Locator.FilterOptions().setHasText(currency)).click();

        return this;
    }

    @Step("Select output currency: {currency}")
    public MoneyTransfersSteps selectCurrencyOutput(String currency) {
        moneyTransfersPage.currencyDropDownOutput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyDropDownOutput.click();
        moneyTransfersPage.currencyItem.filter(new Locator.FilterOptions().setHasText(currency)).click();

        return this;
    }

    @Step("Verify conversion")
    public MoneyTransfersSteps verifyConversion() {
        double rate = getRate();

        String result = moneyTransfersPage.moneyInput.last().inputValue();
        double actual = Double.parseDouble(result);

        String input = moneyTransfersPage.moneyInput.first().inputValue();
        double amount = Double.parseDouble(input);
        double expected = amount * rate;

        double roundedExpected = BigDecimal.valueOf(expected)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        assertTrue(Math.abs(actual - roundedExpected) < 0.001);

        return this;
    }

    @Step("Swap currencies")
    public MoneyTransfersSteps swapCurrencies() {
        moneyTransfersPage.swapButton.click();

        return this;
    }

    public double getRate() {
        String text = moneyTransfersPage.convertionRate.innerText();

        Pattern pattern = Pattern.compile("=\\s*([\\d.]+)");
        Matcher matcher = pattern.matcher(text);

        double rate = 0;
        if (matcher.find()) {
            rate = Double.parseDouble(matcher.group(1));
        }

        return rate;
    }

    @Step("Open remittance fee calculation")
    public MoneyTransfersSteps openRemittanceFeeCalculation() {
        moneyTransfersPage.remittanceFeeCalculation.click();

        return this;
    }

    @Step("Select country Greece")
    public MoneyTransfersSteps selectCountry(String country) {
        moneyTransfersPage.countryDropDown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.countryDropDown.click();
        moneyTransfersPage.countryItem.filter(new Locator.FilterOptions().setHasText(country)).click();

        return this;
    }

    @Step("Verify providers displayed")
    public MoneyTransfersSteps verifyProvidersDisplayed() {
        moneyTransfersPage.cards.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertTrue(moneyTransfersPage.cards.count() > 0);

        return this;
    }

    @Step("Enter invalid amount: {value}")
    public MoneyTransfersSteps enterInvalidAmount(String value) {
        moneyTransfersPage.moneyInput.first().fill(value);

        return this;
    }

    @Step("Verify error message")
    public MoneyTransfersSteps verifyErrorMessage() {
        assertThat(moneyTransfersPage.errorMessage).isVisible();
        assertThat(moneyTransfersPage.errorMessage).containsText(ERROR);

        return this;
    }
}