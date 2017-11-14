package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;

public class ProjectsPage extends CommonPage {

    @FindBy(xpath = "//button[contains(@class, 'add-project')]")
    private WebElement CREATE_NEW_PROJECT_BTN;
    @FindBy(xpath = "//div[contains(@class,'popup-content')]")
    private WebElement POPUP_CONTENT;
    @FindBy(xpath = "//input[contains(@class,'domain')]")
    private WebElement PROJECT_DOMAIN_INPUT;
    @FindBy(xpath = "//input[contains(@class,'input-name')]")
    private WebElement PROJECT_NAME_INPUT;
    @FindBy(xpath = "//button[contains(@class,'save')]")
    private WebElement SAVE_PROJECT_BTN;
    @FindBy(xpath = "//span[contains(@class, 'title-text')]")
    private WebElement PROJECT_TITLE;
    @FindBy(xpath = "//div[contains(@class,'page__out-of-limits')]")
    private WebElement MAXIMUM_PROJECTS_REACHED;

    private final String pageUrl = "/projects";

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    public void isProjectsPage() {
        Assert.assertTrue("\"Projects\" page was not opened!", getDriver().getCurrentUrl().contains(pageUrl));
    }

    public void clickCreateNewProjectButton() {
        CREATE_NEW_PROJECT_BTN.click();
    }

    public void isCreateProjectPopupDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(POPUP_CONTENT));
            Assert.assertTrue("\"Create project\" popup was not opened!", POPUP_CONTENT.isDisplayed());
        } catch (TimeoutException e) {
            if (MAXIMUM_PROJECTS_REACHED.isDisplayed()) {
                Assert.fail("User reached maximum of contracts - please remove one before creating another");
            } else Assert.fail("Project cannot be created by undefined reason - please verify manually");
        }
    }

    final String domain = "test.domain";
    final String name = "New Project";

    public void createProject() {
        PROJECT_DOMAIN_INPUT.sendKeys(domain);
        PROJECT_NAME_INPUT.sendKeys(name);
        SAVE_PROJECT_BTN.click();
        wait.until(ExpectedConditions.invisibilityOfAllElements(Collections.singletonList(POPUP_CONTENT)));
    }

    public void checkProjectIsCreated() {
        Assert.assertThat("Project name is not expected", PROJECT_TITLE.getAttribute("title"), containsString(name));
    }
}

