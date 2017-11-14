package test;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.LandingPage;
import pages.NotesPage;
import pages.ProjectsPage;
import pages.StartPage;
import util.DriverConfiguration;

public class SimpleTest extends DriverConfiguration {

    private StartPage startPage;
    private LandingPage landingPage;
    private NotesPage notesPage;
    private ProjectsPage projectsPage;


    @Before
    public void init() {
        startPage = PageFactory.initElements(driver, StartPage.class);
        landingPage = PageFactory.initElements(driver, LandingPage.class);
        notesPage = PageFactory.initElements(driver, NotesPage.class);
        projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
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
    public void createNewProject(){
        startPage.open();
        startPage.clickLoginButton();
        startPage.login("9nuzumaki@gmail.com", "pass4$emru$h");
        landingPage.isUserLoggedIn();
        landingPage.openProjectsPage();
        projectsPage.isProjectsPage();
        projectsPage.clickCreateNewProjectButton();
        projectsPage.isCreateProjectPopupDisplayed();
        projectsPage.createProject();
        projectsPage.checkProjectIsCreated();
    }
}

