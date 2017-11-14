package pages;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class StartPage extends CommonPage {
    public StartPage(WebDriver driver) throws IOException {
        super(driver);
    }

    //region Public methods
    public void open() {
        getDriver().get(baseUrl);
    }
}