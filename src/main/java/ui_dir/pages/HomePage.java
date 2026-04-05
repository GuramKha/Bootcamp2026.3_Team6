package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static constants.Constants.REJECT_COOKIES;

public class HomePage {
    public Locator declineCookies;

    public HomePage(Page page) {
        this.declineCookies = page.getByText(REJECT_COOKIES);
    }
}

