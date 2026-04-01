package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static constants.Constants.*;

public class MoneyTransfersPage {
    public Locator convertionRate,
            currencyDropDownInput,
            currencyDropDownOutput,
            currencyItem,
            countryDropDown,
            countryItem,
            moneyInput,
            cards,
            remittanceFeeCalculation,
            swapButton,
            errorMessage,
            navbarMoneyTransfers;

    public MoneyTransfersPage(Page page) {
        this.convertionRate = page.locator(".tbcx-pw-exchange-rates-calculator__description");
        this.currencyDropDownInput = page.locator("button.tbcx-field").filter(new Locator.FilterOptions().setHasText(USD_STRING));
        this.currencyDropDownOutput = page.locator("button.tbcx-field").filter(new Locator.FilterOptions().setHasText(GEL_STRING));
        this.currencyItem = page.locator(".tbcx-dropdown-popover-item");
        this.countryDropDown = page.locator(".tbcx-dropdown-selector button.tbcx-field").filter(new Locator.FilterOptions().setHasText(CHOOSE_COUNTRY));
        this.countryItem = page.locator(".tbcx-dropdown-popover-item");
        this.moneyInput = page.locator("div.input-with-label input");
        this.cards = page.locator(".tbcx-pw-money-transfer-fee-calculator__cards .tbcx-pw-card__info");
        this.remittanceFeeCalculation = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(REMITTANCE_FEE)
        );
        this.swapButton = page.locator("//button[.//tbcx-icon[contains(text(),'swap-alt-outlined')]]");
        this.errorMessage = page.locator(".tbcx-pw-money-transfer-fee-calculator__info");
        this.navbarMoneyTransfers = page.locator(".tbcx-pw-breadcrumbs__item")
                .filter(new Locator.FilterOptions().setHasText(MONEY_TRANSFERS));
    }
}