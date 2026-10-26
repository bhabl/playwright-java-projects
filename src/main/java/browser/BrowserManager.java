package browser;

import com.microsoft.playwright.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserManager {
    public Playwright playwright; //used to create an instance of the Chromium, Firefox browser etc.
    public Page page; //is the single tab or window in the browser.
    public BrowserContext context; //is the isolated browser session.
    public Browser browser; //represents the browser instance.
    public Properties properties;
    private static final Logger logger = Logger.getLogger(BrowserManager.class.getName());

    public BrowserManager() {
        properties = new Properties();
        //creates a path to a configuration file. If "config.path" isn't set,
        //it defaults to a file located in "src/main/resources/config.properties
        Path configPath = Paths.get(System.getProperty("config.path",
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
                        "config.properties").toString()));
        try(InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
        }catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load properties file!", e);
        }
    }

    public byte[] takeScreenshot() {
        if (page != null) {
            return page.screenshot();
        }
        return new byte[0];
    }

    public void setUp() {
        logger.info("Setting up Playwright...");
        // Get viewport size of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        playwright = Playwright.create();

        String browserType = properties.getProperty("browser", "chromium");

        switch (browserType.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                logger.warning("Unsupported browser type: " + browserType + ". Defaulting to chromium.");
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
        }
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        logger.info("Playwright setup complete!");
    }

    public void tearDown() {
        logger.info("Tearing down Playwright...");
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        logger.info("Playwright teardown complete!");
    }
}
