package restassured;

import dto.AuthRequestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RegistrationTestsRA {

    String endpoint = "user/registration/usernamepassword";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationSuccess() {

        int i = (int) ((System.currentTimeMillis() / 1000) % 3600);

        AuthRequestDto auth = AuthRequestDto.builder().username("don" + i + "@gmail.com")
                .password("Ddon123456$").build();
        String token = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("token");

        System.out.println(token);
    }

    @Test
    public void registrationWrongEmail() {
        AuthRequestDto auth = AuthRequestDto.builder().username("dongmail.com")
                .password("Ddon123456$").build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.username", containsString("must be a well-formed email address"));

    }

    @Test
    public void registrationWrongPassword() {
        AuthRequestDto auth = AuthRequestDto.builder().username("don@gmail.com")
                .password("Ddon123").build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.password", containsString("At least 8 characters; Must contain at least 1 uppercase letter"));

    }

    @Test
    public void registrationDuplicate() {
        AuthRequestDto auth = AuthRequestDto.builder().username("mara@gmail.com")
                .password("Mmar123456$").build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(409)
                .assertThat().body("message", containsString("User already exists"));
                ;

    }

}
