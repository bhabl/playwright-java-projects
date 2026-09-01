package step_definitions.hooks;

import browser.BrowserManager;
import io.cucumber.java.*;

public class Hooks {
    private final BrowserManager browserManager;

    public Hooks(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    //Runs once before all tests start
    @BeforeAll
    public static void beforeAll() {
        System.out.println("\nExecuting test suite....");
    }

    //Runs once after all tests are done
    @AfterAll
    public static void afterAll() {
        System.out.println("\nFinished executing the test suite!");
    }

    //Runs before each test
    @Before
    public void setup() {
        browserManager.setUp();
    }

    //Runs after each test
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = browserManager.takeScreenshot();
            scenario.attach(screenshot, "image/png", "screenshot");
        }
        browserManager.tearDown();
    }

}
