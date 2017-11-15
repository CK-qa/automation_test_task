package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.*;
import util.DriverConfiguration;

public class SimpleTest extends DriverConfiguration {

    private StartPage startPage;
    private LandingPage landingPage;
    private NotesPage notesPage;
    private ProjectsPage projectsPage;
    private CommonPage commonPage;


    @Before
    public void init() {
        startPage = PageFactory.initElements(driver, StartPage.class);
        landingPage = PageFactory.initElements(driver, LandingPage.class);
        notesPage = PageFactory.initElements(driver, NotesPage.class);
        projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        commonPage = PageFactory.initElements(driver, CommonPage.class);
    }

    @After
    public void logout() {
        commonPage.logOut();
    }

    @Test
    public void simpleLoginTest() {
        startPage.open();
        startPage.clickLoginButton();
        startPage.login("9nuzumaki@gmail.com", "pass4$emru$h");
        landingPage.isUserLoggedIn();
    }

    @Test
    public void createNewNote() {
        notesPage.open();
        notesPage.login("9nuzumaki@gmail.com", "pass4$emru$h");
        notesPage.isNotesPageOpened();
        notesPage.createNewNote();
        notesPage.checkNewNoteAppeared();
    }

    @Test
    public void createNewProject() {
        startPage.open();
        startPage.clickLoginButton();
        startPage.login("9nuzumaki@gmail.com", "pass4$emru$h");
        landingPage.isUserLoggedIn();
        landingPage.openProjectsPage();
        projectsPage.isProjectsPage();
        projectsPage.clickCreateNewProjectButton();
        projectsPage.isCreateProjectPopupDisplayed();
        projectsPage.fillAndSendProjectData();
        projectsPage.checkProjectIsCreated();
    }
}