package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static constants.Constants.MONEY_TRANSFERS;
import static constants.Constants.PERSONAL;

public class CommonPage {
    public Locator personalButton,
            personalButtonOverlay,
            moneyTransfersLink;

    public CommonPage(Page page) {
        this.personalButton = page.locator(".tbcx-pw-header__items").getByText(
                PERSONAL, new Locator.GetByTextOptions().setExact(true));
        this.personalButtonOverlay = page.locator("div.ng-trigger-megaMenuBackgroundAnimation");
        this.moneyTransfersLink = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(MONEY_TRANSFERS).setExact(true)
        );
    }
}
