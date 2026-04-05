package tests.ui;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.FakerSingleton;

import java.io.ByteArrayInputStream;
import java.util.List;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected FakerSingleton faker;

    @Parameters("browser")
    @BeforeClass
    public void setUp(String platform) {
        logger.info("Starting test setup. Platform: " + platform);

        playwright = Playwright.create();

        boolean isCI = "true".equals(System.getenv("CI"));
        boolean headless = isCI;

        switch (platform.toLowerCase()) {
            case "firefox" -> {
                logger.info("Launching Firefox browser");

                BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                        .setHeadless(headless);

                browser = playwright.firefox().launch(options);
            }
            default -> {
                logger.info("Launching Chromium browser");

                BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                        .setHeadless(headless);

                if (isCI) {
                    logger.debug("Running in CI mode, applying additional browser args");

                    options.setArgs(List.of(
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--disable-gpu"
                    ));
                }

                browser = playwright.chromium().launch(options);
            }
        }

        logger.debug("Creating browser context with viewport 1920x1080");

        browserContext = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(1920, 1080)
        );

        page = browserContext.newPage();
        logger.info("New page created");

        faker = FakerSingleton.getInstance();
    }

    @AfterMethod
    public void captureOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: " + result.getName());

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
        logger.info("Starting teardown");

        if (page != null) {
            logger.debug("Closing page");
            page.close();
        }

        if (browserContext != null) {
            logger.debug("Closing browser context");
            browserContext.close();
        }

        if (browser != null) {
            logger.debug("Closing browser");
            browser.close();
        }

        if (playwright != null) {
            logger.debug("Closing Playwright");
            playwright.close();
        }

        logger.info("Teardown completed");
    }
}