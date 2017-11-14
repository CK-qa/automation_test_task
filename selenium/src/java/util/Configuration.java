package util;

import org.openqa.selenium.WebDriver;

public class Configuration extends DriverConfiguration {

    public Configuration(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver driver;
}
