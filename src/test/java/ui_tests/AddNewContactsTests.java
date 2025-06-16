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
        Assert.assertEquals(sizeBeforeAdd +1, sizeAfterAdd);
    }

    @Test
    public void addNewContactNegativeTest_EmptyName(){
        Contact contact = Contact.builder()
                .name("")
                .lastName(generateString(10))
                .phone("05" + generatePhone(8))
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        String nameFieldValue = addPage.getInputValueByPlaceholder("Name");
        Assert.assertEquals(nameFieldValue, "", "Input Name without value");
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        Assert.assertNotEquals(sizeBeforeAdd+1, sizeAfterAdd);
    }

    @Test
    public void addNewContactNegativeTest_EmptyLastName(){
        Contact contact = Contact.builder()
                .name(generateString(8))
                .lastName("")
                .phone("05" + generatePhone(8))
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        String nameFieldValue = addPage.getInputValueByPlaceholder("Last Name");
        Assert.assertEquals(nameFieldValue, "", "Input Last Name without value");
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        Assert.assertNotEquals(sizeBeforeAdd+1, sizeAfterAdd);
    }

    @Test
    public void addNewContactNegativeTest_EmptyPhone(){
        Contact contact = Contact.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone("")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        addPage.closeAlert();
        String nameFieldValue = addPage.getInputValueByPlaceholder("Phone");
        Assert.assertEquals(nameFieldValue, "", "Input Phone without value");
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        Assert.assertNotEquals(sizeBeforeAdd+1, sizeAfterAdd);
    }

    @Test
    public void addNewContactNegativeTest_EmptyAddress(){
        Contact contact = Contact.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone("05"+generatePhone(9))
                .email(generateEmail(10))
                .address("")
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        String nameFieldValue = addPage.getInputValueByPlaceholder("Address");
        Assert.assertEquals(nameFieldValue, "", "Input Address without value");
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        Assert.assertNotEquals(sizeBeforeAdd+1, sizeAfterAdd);
    }

    @Test
    public void addNewContactNegativeTest_InvalidPhone(){
        Contact contact = Contact.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone("05"+generatePhone(3))
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        addPage.closeAlert();
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        Assert.assertNotEquals(sizeBeforeAdd+1, sizeAfterAdd);
    }
    @Test
    public void addNewContactNegativeTest_InvalidEmail(){
        Contact contact = Contact.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone("05"+generatePhone(9))
                .email("sotiga2007mail.ru")
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.typeNewContactForm(contact);
        addPage.closeAlert();
        int sizeAfterAdd = contactsPage.getContactsListSizeUseFindElement();
        Assert.assertNotEquals(sizeBeforeAdd+1, sizeAfterAdd);
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

    @Test
    public void addNewContactPositiveTest_validateAll() {
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
        contactsPage.clickContactCardByName(contact.getName());
        Assert.assertTrue(contactsPage.validateContactAll(contact.getName(), contact.getLastName(),
                contact.getPhone(), contact.getEmail(), contact.getAddress(),
                contact.getDescription()));
    }
}