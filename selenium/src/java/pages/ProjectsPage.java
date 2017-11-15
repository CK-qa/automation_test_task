package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
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
    @FindBy(xpath = "//span[contains(@class,'-settings')]")
    private WebElement SETTINGS_BTN;
    @FindBy(xpath = "//a[contains(@class,'js-remove')]")
    private WebElement REMOVE;
    @FindBy(xpath = "//div[contains(@class,'-delete')]")
    private WebElement DELETE_PROJECT_POPUP;
    @FindBy(xpath = "//input[contains(@class,'remove-input')]")
    private WebElement PROJECT_REMOVE_NAME_INPUT;
    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement DELETE_BTN;

    private final String pageUrl = "/projects";

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    //region Public methods
    public void isProjectsPage() {
        Assert.assertTrue("\"Projects\" page was not opened!", getDriver().getCurrentUrl().contains(pageUrl));
    }

    public void clickCreateNewProjectButton() {
        if (getDriver().getCurrentUrl().contains("no-projects")) {
            createProject();
        } else {
            deleteProject();
            createProject();
        }
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

    public void fillAndSendProjectData() {
        PROJECT_DOMAIN_INPUT.sendKeys(domain);
        PROJECT_NAME_INPUT.sendKeys(name);
        SAVE_PROJECT_BTN.click();
        wait.until(ExpectedConditions.invisibilityOfAllElements(Collections.singletonList(POPUP_CONTENT)));
    }

    public void checkProjectIsCreated() {
        Assert.assertThat("Project name is not expected", PROJECT_TITLE.getAttribute("title"), containsString(name));
    }
    //endregion

    //region Private methods
    private void createProject() {
        if (getDriver().getCurrentUrl().contains("no-projects")) {
            CREATE_NEW_PROJECT_BTN.click();
        }
    }

    private void deleteProject() {
        SETTINGS_BTN.click();
        wait.until(ExpectedConditions.elementToBeClickable(REMOVE));
        //click to expandable section with js
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",REMOVE);
        REMOVE.click();

        wait.until(ExpectedConditions.visibilityOf(DELETE_PROJECT_POPUP));
        PROJECT_REMOVE_NAME_INPUT.sendKeys(name);
        DELETE_BTN.click();
        wait.until(ExpectedConditions.invisibilityOfAllElements(Collections.singletonList(DELETE_BTN)));
    }
    //endregion
}