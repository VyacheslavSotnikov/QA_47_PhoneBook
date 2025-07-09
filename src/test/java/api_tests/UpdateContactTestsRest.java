package api_tests;

import dto.*;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class UpdateContactTestsRest extends ContactController {

    Contact contact;

    @BeforeClass(alwaysRun = true)
    public void createContact(){
        contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        if(response.getStatusCode() != 200){
            System.out.println("Contact doesn't created");
        } else
            responseMessageDto = response.body().as(ResponseMessageDto.class);
        //Contact was added! ID: 1ebfbba2-b59f-45c9-8540-d2b0c07f1649)
        contact.setId(responseMessageDto.getMessage().split("ID: ")[1]);
    }

    @Test(groups = "smoke")
    public void  updateContactPositiveTest(){
        System.out.println(contact.toString());
        contact.setName("New_name");
        Response response = updateContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        response
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))
                ;
        ResponseMessageDto responseMessageDto = response.body().as(ResponseMessageDto.class);
        Assert.assertTrue(responseMessageDto.getMessage().contains("Contact was updated"));
    }
}
