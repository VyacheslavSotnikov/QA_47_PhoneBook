package ui_tests;

import dto.User;
import manager.ApplicationManager_PB;
import org.testng.annotations.Test;
import pages.HomePage_PB;
import pages.LoginPage_PB;

public class LoginTests_PB extends ApplicationManager_PB {

    @Test
    public void loginPositiveTest() {
        User user = new User ("sotiga2009@gmail.com", "Sh12345!@");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeLoginForm(user);
    }
}
