package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends ApplicationManager{

    @Test
    public void loginPositiveTest() {
        HomePage homePage = new HomePage((getDriver()));
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm("sotiga2009@gmail.com", "Sh12345!@");
    }
}
