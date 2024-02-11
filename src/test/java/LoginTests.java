import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class LoginTests {
    @Test
    void successfulLoginTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}";
        given()
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulLoginTest() {
        given()
                .log().uri()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);

    }

    @Test
    void unsuccessfulLogin400Test() {
        String authData = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}";
        given()
                .body(authData)
                //.contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @Test
    void userNotFoundTest() {
        String authData = "{\"email\": \"eve.holt@reqres232.in\",\"password\": \"cityslicka\"}";
        given()
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("user not found"));
        ;
    }

    @Test
    void MissingPasswordTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
