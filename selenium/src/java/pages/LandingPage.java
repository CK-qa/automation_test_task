package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class LandingPage extends CommonPage {

    public LandingPage(WebDriver driver) throws IOException {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(@class, 'management-link')][contains(@href,'/projects')]")
    private WebElement PROJECTS_LINK;


    public void isUserLoggedIn() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_ICON));
        Assert.assertTrue("User icon is not displayed after attempt to log in", LOGIN_ICON.isDisplayed());
    }

    public void openProjectsPage() {
        PROJECTS_LINK.click();
    }
}

