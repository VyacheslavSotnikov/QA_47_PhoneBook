package ui_tests;

import dto.Contact;
import dto.UserLombok;
import manager.ApplicationManager_PB;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage_PB;
import pages.LoginPage_PB;
import utils.HeaderMenuItem;

import static pages.BasePage_PB.*;
import static utils.RandomUtils.*;

public class AddNewContactsTests extends ApplicationManager_PB {

    HomePage_PB homePagePb;
    LoginPage_PB loginPagePb;
    ContactsPage contactsPage;
    AddPage addPage;
    int sizeBeforeAdd;

    @BeforeMethod
    public void goToAddPage(){
        UserLombok user = new UserLombok("sotiga2007@mail.ru", "Sh12345!@");

        homePagePb = new HomePage_PB(getDriver());
        loginPagePb = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPagePb.typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
        sizeBeforeAdd = contactsPage.getContactListSize();
        addPage = clickButtonHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest(){
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("05" + generatePhone(8))
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        System.out.println(sizeBeforeAdd + "X" + sizeAfterAdd);
        Assert.assertEquals(sizeBeforeAdd +1, sizeAfterAdd);
    }

    @Test
    public void addNewContactPositiveTest_validateContactNamePhone() {
        Contact contact = Contact.builder()
                .name("Name-"+generateString(8))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        Assert.assertTrue(contactsPage.validateContactNamePhone(contact.getName(), contact.getPhone()));
    }
}