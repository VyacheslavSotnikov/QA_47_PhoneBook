package ui_tests;

import dto.UserLombok;
import manager.ApplicationManager_PB;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage_PB;
import pages.LoginPage_PB;
import utils.TestNGListener;

import java.lang.reflect.Method;

@Listeners(TestNGListener.class)

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
    public void loginPositiveTest(Method method) {
        logger.info("start method " + method.getName());

        UserLombok userLombok = UserLombok.builder()
                .username ("sotiga2015@gmail.com")
                .password("Sh12345!@")
                .build();
        logger.info("test data -->"+ userLombok.toString());
        loginPagePB.typeLoginForm(userLombok);
    }
}
