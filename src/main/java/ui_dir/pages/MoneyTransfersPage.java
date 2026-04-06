package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static constants.Constants.*;

public class MoneyTransfersPage {
    private final Page page;
    public Locator moneyTransfersNavbar,
            currencyDropdownSelectorItem,
            currencySelectorItem,
            dropdownList,
            currencyInput,
            conversionRate,
            swapCurrenciesButton,
            remittanceFeeCalculation,
            countryDropdown,
            cards,
            errorMessage;

    public MoneyTransfersPage(Page page) {
        this.page = page;
        this.moneyTransfersNavbar = page.locator(".tbcx-pw-breadcrumbs__item").getByRole(
                AriaRole.LINK,
                new Locator.GetByRoleOptions().setName(MONEY_TRANSFERS).setExact(true)
        );
        this.dropdownList = page.locator(".tbcx-dropdown-popover");
        this.currencyInput = page.locator(".input-with-label .input");
        this.conversionRate = page.locator(".tbcx-pw-exchange-rates-calculator__description");
        this.swapCurrenciesButton = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText(SWAP_CURRENCY));
        this.remittanceFeeCalculation = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(REMITTANCE_FEE)
        );
        this.countryDropdown = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(CHOOSE_COUNTRY).setExact(true)
        );
        this.cards = page.locator(".tbcx-pw-money-transfer-fee-calculator__cards");
        this.errorMessage = page.locator(".tbcx-pw-money-transfer-fee-calculator__info");
    }

    public Locator currencyDropdownSelector(String currency) {
        return currencyDropdownSelectorItem = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(currency)
        );
    }

    public Locator currencySelector(String currency) {
        return currencySelectorItem = page.locator(".tbcx-dropdown-popover")
                .getByText(
                        currency,
                        new Locator.GetByTextOptions().setExact(true)
                );
    }

    public Locator countrySelector(String country) {
        return page.locator(".tbcx-dropdown-popover-item__title")
                .getByText(country, new Locator.GetByTextOptions().setExact(true));
    }
}
