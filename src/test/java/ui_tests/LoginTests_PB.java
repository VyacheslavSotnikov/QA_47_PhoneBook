package ui_tests;

import dto.UserLombok;
import manager.ApplicationManager_PB;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage_PB;
import pages.LoginPage_PB;

public class LoginTests_PB extends ApplicationManager_PB {

    private LoginPage_PB loginPagePB;

    @BeforeMethod
    public void goToLoginPage(){
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        loginPagePB = new LoginPage_PB(getDriver());
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        UserLombok userLombok = UserLombok.builder()
                .username ("sotiga2015@gmail.com")
                .password("123")
                        .build();
        loginPagePB.typeLoginForm(userLombok);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMessageLogIn401());
    }

    @Test
    public void loginNegativeTest_EmptyPassword() {
        UserLombok userLombok = UserLombok.builder()
                .username ("sotiga2015@gmail.com")
                .password("")
                .build();
        loginPagePB.typeLoginForm(userLombok);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMessageLogIn401());
    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        UserLombok userLombok = UserLombok.builder()
                .username ("sotiga2015gmail.com")
                .password("Sh12345!@")
                .build();
        loginPagePB.typeLoginForm(userLombok);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMessageLogIn401());
    }

    @Test
    public void loginNegativeTest_EmptyEmail() {
        UserLombok userLombok = UserLombok.builder()
                .username ("")
                .password("Sh12345!@")
                .build();
        loginPagePB.typeLoginForm(userLombok);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMessageLogIn401());
    }

    @Test
    public void loginPositiveTest() {
        UserLombok userLombok = UserLombok.builder()
                .username ("sotiga2015@gmail.com")
                .password("Sh12345!@")
                .build();
        loginPagePB.typeLoginForm(userLombok);
    }

    @Test
    public void registrationPositiveTest() {
        User user = new User ("sotiga2021@gmail.com", "Sh12345!@");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeRegistrationForm(user);
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        User user = new User ("sotiga2020gmail.com", "Sh12345!@");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeRegistrationForm(user);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMassagePresent("Registration failed with code 400"));
    }

    @Test
    public void registrationNegativeTest_wrongPassword() {
        User user = new User ("sotiga2020@gmail.com", "111");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeRegistrationForm(user);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMassagePresent("Registration failed with code 400"));
    }

    @Test
    public void registrationNegativeTest_existEmail() {
        User user = new User ("sotiga2020@gmail.com", "Sh12345!@");
        HomePage_PB homePagePB = new HomePage_PB((getDriver()));
        homePagePB.clickBtnLoginHeader();
        LoginPage_PB loginPagePB = new LoginPage_PB(getDriver());
        loginPagePB.typeRegistrationForm(user);
        loginPagePB.closeAlert();
        Assert.assertTrue(loginPagePB.isErrorMassagePresent("Registration failed with code 409"));
    }
}
