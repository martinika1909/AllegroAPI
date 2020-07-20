import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class GetRequestHandler
{
    public static Response sendGetRequest(String bearerToken, String getRequest, int expectedHttpCode)
    {
        RestAssured.baseURI = "https://api.allegro.pl";
        Response response =
                given().
                        header("Authorization", "Bearer " + bearerToken).
                        header("Accept", "application/vnd.allegro.public.v1+json").
                        when().
                        get(getRequest);

        Assert.assertEquals(response.statusCode(), expectedHttpCode);
        return response;
    }
}
