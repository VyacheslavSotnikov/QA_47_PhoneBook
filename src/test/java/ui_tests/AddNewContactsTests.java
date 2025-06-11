package ui_tests;

import dto.Contact;
import dto.UserLombok;
import manager.ApplicationManager_PB;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.HomePage_PB;
import pages.LoginPage_PB;
import utils.HeaderMenuItem;

import static pages.BasePage_PB.*;
import static utils.RandomUtils.*;

public class AddNewContactsTests extends ApplicationManager_PB {

    private AddPage addPage;

    @BeforeMethod
    public void goToAddPage(){
        UserLombok user = new UserLombok("sotiga2007@mail.ru", "Sh12345!@");
        HomePage_PB homePage = new HomePage_PB(getDriver());
        LoginPage_PB loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user);
        clickButtonHeader(HeaderMenuItem.ADD);
        addPage = new AddPage(getDriver());
    }

    @Test
    public void addNewContactPositiveTest(){
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastname(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc "+ generateString(15))
                .build();
        addPage.typeAddPageForm(contact);
    }

    @Test
    public void addNewContactNegativeTest_EmptyAll() {
        Contact contact = Contact.builder()
                .name("")
                .lastname("")
                .phone("")
                .email("")
                .address("")
                .description("")
                .build();
        addPage.typeAddPageForm(contact);
        addPage.closeAlert();
    }
}
