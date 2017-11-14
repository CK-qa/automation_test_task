package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Configuration;
import util.PropertyLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonPage extends Configuration {
    public CommonPage(WebDriver driver) {
        super(driver);
    }

    public String baseUrl = PropertyLoader.loadProperty("site.url");

    @FindBy(xpath = "//s-icon[contains(@icon,'user')]")
    public WebElement LOGIN_ICON;
    @FindBy(xpath = "//button[contains(@class,'authentication-login')]")
    private WebElement LOGIN_BTN;
    @FindBy(xpath = "//div[contains(@class,'popup__content')]")
    private WebElement POPUP_CONTENT;
    @FindBy(xpath = "//input[@type='email' and @name='email']")
    private WebElement EMAIL_INPUT;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement PWD_INPUT;
    @FindBy(xpath = "//div[contains(@class,'-chosen')]")
    private WebElement SELECTED_TAB;
    @FindBy(xpath = "//div[contains(@data-tab,'log in')]")
    private WebElement SELECTABLE_LOGIN_BTN;
    @FindBy(xpath = "//button[@data-test='auth-popup__submit']")
    private WebElement SUBMIT_BTN;


    WebDriverWait wait = new WebDriverWait(driver, 25);

    public WebDriver getDriver() {
        try {
            driver.findElement(By.tagName("html"));
        } catch (NoSuchWindowException ignored) {
        }
        return driver;
    }

    public void clickLoginButton() {
        LOGIN_BTN.click();
        Assert.assertTrue("Popup is not displayed after Login button was clicked", POPUP_CONTENT.isDisplayed());
    }

    public void login(String username, String password) {
        if (isLoginTabPreselected()) {
            EMAIL_INPUT.sendKeys(username);
            PWD_INPUT.sendKeys(password);
        } else {
            switchToLoginTab();
            EMAIL_INPUT.sendKeys(username);
            PWD_INPUT.sendKeys(password);
        }
        SUBMIT_BTN.click();
        wait.until(ExpectedConditions.visibilityOf(LOGIN_ICON));
    }

    public static String currentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    //region Private methods
    private void switchToLoginTab() {
        SELECTABLE_LOGIN_BTN.click();
    }

    public boolean isLoginTabPreselected() {
        return SELECTED_TAB.getText().contains("Log in");
    }
    //endregion

}



