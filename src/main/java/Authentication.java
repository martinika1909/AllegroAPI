import io.restassured.*;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Base64;

import static io.restassured.RestAssured.given;

class Authentication
{
    static String getBearerToken()
    {
        String clientId = "50b3a36abf3f4def8e166f1acca9ed37";
        String clientSecret = "kJSrEbXIM44g9DRno1tfPGvsc0urw3r89YoSJsLNvrbitEzYWtnzXvOKFwqkqhwx";
        String token = clientId + ":" + clientSecret;

        RestAssured.baseURI = "https://allegro.pl";
        Response response =
                given().
                        param("grant_type", "client_credentials").
                        header("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes())).
                when().
                        post("/auth/oauth/token");

        Assert.assertEquals(response.statusCode(), 200);
        return response.jsonPath().get("access_token");
    }
}
