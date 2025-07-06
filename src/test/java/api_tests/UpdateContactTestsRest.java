package api_tests;

import dto.*;
import groovy.xml.slurpersupport.GPathResult;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class UpdateContactTestsRest extends ContactController {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void updateNewContactPositiveTest(){
        Response getAllResponse = getAllUserContacts();
        softAssert.assertEquals(getAllResponse.getStatusCode(), 200, "Get all contacts failed");


        ContactsDto contactsDto = getAllResponse.getBody().as(ContactsDto.class);
        Contact[] allContacts = contactsDto.getContacts();
        if (allContacts.length == 0) {
            softAssert.fail("No contacts available to update");
            softAssert.assertAll();
            return;
        }
        Contact contactToUpdate = getRandomContact(allContacts);
        String oldName = contactToUpdate.getName();
        String newName = oldName + "name_updated";

        String oldLastName = contactToUpdate.getLastName();
        String newLastName = oldLastName + "lastName_updated";

        contactToUpdate.setName(newName);
        contactToUpdate.setLastName(newLastName);
        contactToUpdate.setAddress("Updated Address");

        Response updateResponse = updateContactRequest(contactToUpdate, tokenDto);
        softAssert.assertEquals(updateResponse.getStatusCode(), 200, "Update contact failed");

        ResponseMessageDto messageDto = updateResponse.getBody().as(ResponseMessageDto.class);
        System.out.println("Update response message: " + messageDto.getMessage());

        Response updatedListResp = getAllUserContacts();
        Contact[] updatedContacts = updatedListResp.getBody().as(ContactsDto.class).getContacts();
        boolean isUpdated = false;

        for (Contact c : updatedContacts) {
            if (c.getId().equals(contactToUpdate.getId()) && c.getName().equals(newName)) {
                isUpdated = true;
                break;
            }
        }

        softAssert.assertTrue(isUpdated, "Updated contact not found in the list");

        softAssert.assertAll();
    }

    @Test
    public void updateContactNegativeTest_invalidPhone() {
        SoftAssert softAssert = new SoftAssert();

        Response getAllResponse = getAllUserContacts();
        softAssert.assertEquals(getAllResponse.getStatusCode(), 200, "Get all contacts failed");

        ContactsDto contactsDto = getAllResponse.getBody().as(ContactsDto.class);
        Contact[] allContacts = contactsDto.getContacts();

        if (allContacts.length == 0) {
            softAssert.fail("No contacts to test update");
            softAssert.assertAll();
            return;
        }

        Contact contactToUpdate = getRandomContact(allContacts);
        contactToUpdate.setPhone("abc123");

        Response response = updateContactRequest(contactToUpdate, tokenDto);
        softAssert.assertEquals(response.getStatusCode(), 400, "Any format error");

        System.out.println("Error response body:");
        response.prettyPrint();

        softAssert.assertAll();
    }

    @Test
    public void UpdateContactNegativeTest_wrongToken(){
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        Response response = updateContactRequest(contact, TokenDto.builder()
                .token("wrong")
                .build());
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getStatus(), 401);
        softAssert.assertTrue(errorMessageDto.getMessage().toString()        .contains("JWT strings must contain exactly 2 period characters."));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertAll();
    }

    @Test
    public void updateContactIdNotFoundTest() {
        Contact contact = Contact.builder()
                .id(UUID.randomUUID().toString())  // валидный, но несуществующий
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();

        Response response = updateContactRequest(contact, tokenDto);

        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        Assert.assertTrue(response.getBody().asString().contains("not found in your contacts"));
    }
}
