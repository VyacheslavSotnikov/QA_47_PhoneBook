package ui_tests;

import dto.UserLombok;
import manager.ApplicationManager_PB;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage_PB;
import pages.LoginPage_PB;

import  static  utils.RandomUtils.*;

public class RegistrationTests extends ApplicationManager_PB {

    HomePage_PB homePagePB;
    LoginPage_PB loginPagePB;

    @BeforeMethod
    public void goToRegistrationPage(){
        homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        loginPagePB = new LoginPage_PB(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        UserLombok userLombok = new UserLombok(generateEmail(10), "Password123!");
        loginPagePB.typeRegistrationForm(userLombok);
        Assert.assertTrue(loginPagePB.isNoContactMessagePresent("Add new by clicking on Add in NavBar!"));
    }

    @Test
    public void registrationPositiveTest_dublicateUser() {
        UserLombok userLombok = new UserLombok(generateEmail(10), "Password123!");
        loginPagePB.typeRegistrationForm(userLombok);
        if(loginPagePB.isNoContactMessagePresent("Add new by clicking on Add in NavBar!")){
            loginPagePB.logOut();
            loginPagePB.typeRegistrationForm(userLombok);
            Assert.assertTrue(loginPagePB.closeAlertReturnText().contains("User already exist"));
        } else{
            Assert.fail("Wrong registration with user " + userLombok.toString());
        };
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        UserLombok userLombok = new UserLombok("sotiga2020gmail.com", "Password123!");
        loginPagePB.typeRegistrationForm(userLombok);
        Assert.assertTrue(loginPagePB.closeAlertReturnText().contains("Password must contain at least one special symbol from [‘$’,’~’,’-‘,’_’]!"));
    }

    @Test
    public void registrationNegativeTest_wrongPassword() {
        UserLombok userLombok = new UserLombok(generateEmail(10), "111");
        loginPagePB.typeRegistrationForm(userLombok);
        Assert.assertTrue(loginPagePB.closeAlertReturnText().contains("Password must contain at least one special symbol from [‘$’,’~’,’-‘,’_’]!"));
    }
}
