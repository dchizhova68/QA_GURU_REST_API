import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class reqresInTests {

    @Test
    void userEmailTest() {
        given()
                .log().uri()
                .get("https://reqres.in/api/users/3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("emma.wong@reqres.in"));
    }

    @Test
    void succsessfulCreateUserTest() {
        String authData = "{\"name\": \"Diana Rose\", \"job\":\"Chemist\"}";
        given()
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Diana Rose"))
                .body("job", is("Chemist"));
    }

    @Test
    void unsuccsessfulRegisterTest() {
        String authData = "{\"email\": \"Diana Rose\", \"password\":\"passW0rd\"}";
        given()
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void listUserTest() {
        given()
                .log().uri()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.findAll {it.id = 3}.email", hasItem("emma.wong@reqres.in"));
    }

    @Test
    void resoursNotFoundest() {
        given()
                .log().uri()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }


}
