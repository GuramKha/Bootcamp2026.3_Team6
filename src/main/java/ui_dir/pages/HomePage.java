package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static constants.Constants.ACCEPT_ALL;

public class HomePage {
    public Locator cookiesAcceptButton;

    public HomePage(Page page) {
        this.cookiesAcceptButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName(ACCEPT_ALL)
        );
    }
}
