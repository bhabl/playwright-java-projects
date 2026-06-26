import com.microsoft.playwright.*;



import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screensize.getWidth();
        int height = (int) screensize.getHeight();
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext bc = browser.newContext(new Browser.NewContextOptions().setViewportSize(width,height));
            Page page = bc.newPage();
            page.navigate("https://www.webdriveruniversity.com");
            System.out.println(page.title());
            page.close();
        }
    }
}