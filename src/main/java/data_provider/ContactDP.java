package data_provider;

import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class ContactDP {

    @DataProvider
    public  Contact[] addNewContactDP(){
        Contact contact1 = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("05" + generatePhone(8))
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        return new Contact[]{contact1};
    }

    @DataProvider
    public Iterator <Contact> addNewContactDPFile(){
        List<Contact> list = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/main/resources/DataProvider/contactFile.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",");
                list.add(Contact.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .phone(splitArray[2])
                        .email(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return list.listIterator();
    }
}
