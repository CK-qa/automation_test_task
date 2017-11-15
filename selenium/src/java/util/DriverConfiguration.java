package util;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DriverConfiguration {

    private static String gridHubUrl;
    private static String baseUrl;
    private static Capabilities capabilities;

    protected WebDriver driver;

    @Before
    public void setUp() throws Throwable {
        baseUrl = PropertyLoader.loadProperty("site.url");
        gridHubUrl = PropertyLoader.loadProperty("grid.url");
        if ("".equals(gridHubUrl)) {
            gridHubUrl = null;
        }
        System.setProperty("user.country", "US");
        System.setProperty("webdriver.chrome.driver", "/Users/victoriabozhko/Selenium/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--dns-prefetch-disable");
        capabilities = PropertyLoader.loadCapabilities();

        driver = WebDriverPool.DEFAULT.getDriver(capabilities);

        driver.manage().deleteAllCookies();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Rule
    public TestName name = new TestName();

    @Rule
    public TestRule testWatcher = new TestWatcher() {
        @Override
        @Before
        public void failed(Throwable t, Description test) {
            if (driver instanceof TakesScreenshot) {
                File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(tempFile, new File("screenshots/" + new SimpleDateFormat("yyyy.MM.dd_hh-mm-ss").format(new Date()) + "_" + name.getMethodName() + ".png"));
                } catch (IOException e) {
                    // TODO handle exception
                }
            }
        }

//        @Override
//        @Before
//        public void finished(Description description) {
//            driver.quit();
//        }

        @After
        public void tearDown() {
            driver.quit();
        }
    };
}
