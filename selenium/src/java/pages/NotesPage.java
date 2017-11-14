package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class NotesPage extends CommonPage {

    private String pageUrl = "/notes";

    @FindBy(xpath = "//div[contains(@class,'title -xxl')]")
    private WebElement NOTES_TITLE;
    @FindBy(xpath = "//span[contains(text(), 'Add note')]")
    private WebElement ADD_NOTE_BTN;
    @FindBy(xpath = "//div[contains(@class,'popup notes')]")
    private WebElement NOTES_POPUP_CONTENT;
    @FindBy(xpath = "//input[contains(@data-cream-ui, 'input-title')]")
    private WebElement TITLE_INPUT_FIELD;
    @FindBy(xpath = "//textarea[contains(@data-cream-ui, 'input-note')]")
    private WebElement DESCRIPTION_INPUT_FIELD;
    @FindBy(xpath = "//div[contains(@data-cream-region, 'datetime')]")
    private WebElement PUBLICATION_DATE;
    @FindBy(xpath = "//div[contains(@data-cream-ui, 'advanced-text')]")
    private WebElement PUBLICATION_DOMAIN;
    @FindBy(xpath = "//button[contains(@data-cream-action, 'save')]")
    private WebElement SEND_NOTE_BTN;
    @FindBy(xpath = "//div[@data-cream-value='delete']")
    private WebElement DELETE_NOTE;
    @FindBy(xpath = "//div[contains(@class,'notes-dialog cream-popup')]")
    private WebElement CONFIRMATION_POPUP;
    @FindBy(xpath = "//span[contains(@class,'btn__text')][contains(text(),'Yes')]")
    private WebElement YES_BUTTON;

    public NotesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        getDriver().get(baseUrl + pageUrl);
    }

    public void isNotesPageOpened() {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(NOTES_TITLE));
        Assert.assertTrue("\"Notes\" page title is not displayed", NOTES_TITLE.isDisplayed());
        Assert.assertThat("\"Notes\" page title is incorrect", NOTES_TITLE.getText(), containsString("Notes"));
    }

    private static final String title = Utils.getSaltString(7);
    private static final String description = Utils.getSaltString(17);

    public void createNewNote() {
        clickCreateNewNoteButton();
        if (isPopupContentDisplayed()) {
            enterNoteData(title, description);
            clickSaveNoteButton();
            wait.until(ExpectedConditions.invisibilityOfAllElements(Collections.singletonList(NOTES_POPUP_CONTENT)));
            System.out.println(title);
        } else Assert.fail("\"Add Note\" popup wasn't opened");
    }

    public void checkNewNoteAppeared() {
        Assert.assertTrue("New note didn't appear in the table", isCreatedNotePresented());
    }

    private boolean isCreatedNotePresented() {
        wait.until(ExpectedConditions.visibilityOf(NOTES_TITLE));
        List<WebElement> rowsPerDate = getDriver().findElements(By.xpath("//td[contains(text(), '" + CommonPage.currentDate() + "')]/.."));
        for (WebElement row : rowsPerDate) {
            System.out.println(row.getText());
            if (row.getText().contains(title)) {
                return true;
            }
        }
        return false;
    }

    private void clickCreateNewNoteButton() {
        ADD_NOTE_BTN.click();
    }

    private boolean isPopupContentDisplayed() {
        return NOTES_POPUP_CONTENT.isDisplayed();
    }

    private void enterNoteData(String title, String description) {
        TITLE_INPUT_FIELD.sendKeys(title);
        Assert.assertThat("Publication date is preset with incorrect date", PUBLICATION_DATE.getText(), containsString(CommonPage.currentDate()));
        Assert.assertThat("Publication default domain is preset with incorrect value", PUBLICATION_DOMAIN.getText(), containsString("Domain Analytics"));
        DESCRIPTION_INPUT_FIELD.sendKeys(description);
    }

    private void clickSaveNoteButton() {
        SEND_NOTE_BTN.click();
    }
}
