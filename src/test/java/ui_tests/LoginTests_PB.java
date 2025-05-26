package ui_tests;

import manager.ApplicationManager_PB;
import org.testng.annotations.Test;
import pages.HomePage_PB;
import pages.LoginPage_PB;

public class LoginTests_PB extends ApplicationManager_PB {

    @Test
    public void loginPositiveTest() {
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeLoginForm("sotiga2009@gmail.com", "Sh12345!@");
    }
}
