package ui_tests;

import dto.User;
import manager.ApplicationManager_PB;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage_PB;
import pages.LoginPage_PB;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

public class LoginTests_PB extends ApplicationManager_PB {

    @Test
    public void loginNegativeTest_wrongPassword() {
        User user = new User ("sotiga2015@gmail.com", "Sh12345!2");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeLoginForm(user);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMassagePresent("Login Failed with code 401"));

    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        User user = new User ("sotiga2015gmail.com", "Sh12345!2");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeLoginForm(user);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMassagePresent("Login Failed with code 401"));
    }

    @Test
    public void loginPositiveTest() {
        User user = new User ("sotiga2009@gmail.com", "Sh12345!@");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeLoginForm(user);
    }
}
