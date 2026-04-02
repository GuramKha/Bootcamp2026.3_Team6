package tests.ui;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.ByteArrayInputStream;
import java.util.List;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();

        boolean isCI = "true".equals(System.getenv("CI"));

        if (isCI) {
            options.setHeadless(true);
            options.setChannel("chrome");
            options.setArgs(List.of(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu"
            ));
        } else {
            options.setHeadless(false);
        }

        browser = playwright.chromium().launch(options);
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));

        page = browserContext.newPage();
    }

    @AfterMethod
    public void captureOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );

            Allure.addAttachment(
                    "Screenshot - " + result.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );

            Allure.addAttachment(
                    "Page HTML - " + result.getName(),
                    "text/html",
                    new ByteArrayInputStream(page.content().getBytes()),
                    "html"
            );

            Allure.addAttachment(
                    "Failed URL - " + result.getName(),
                    "text/plain",
                    new ByteArrayInputStream(page.url().getBytes()),
                    "txt"
            );
        }
    }

    @AfterClass
    public void tearDown() {
        if (page != null) page.close();
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
