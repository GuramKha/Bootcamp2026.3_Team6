package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    public Locator cookiesAcceptButton;

    public HomePage(Page page) {
        this.cookiesAcceptButton = page.locator("//button[normalize-space()='Accept All']");
    }
}
