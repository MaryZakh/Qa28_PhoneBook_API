package restassured;

import dto.ContactDto;
import dto.GetAllContactsDto;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetAllContactsTestsRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTc0Mzk1MzYwNywiaWF0IjoxNzQzMzUzNjA3fQ.U5wGB2rQekt-LTEGSijdW6PDexDuK-D135pcwUh0buE";
    String endpoint = "contacts";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }


    @Test
    public void getAllContactsSuccess(){
GetAllContactsDto contactsDto = given()
                .header("Authorization",token)
                .when()
                .get(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(GetAllContactsDto.class);
        List<ContactDto>list = contactsDto.getContacts();
        for(ContactDto contact:list){
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("Size of list --->"+ list.size());
        }
    }
}
