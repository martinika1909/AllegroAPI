import categories.MainCategories;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CategoriesAndParametersScenarios
{
    private static String BaseGetRequest = "/sale/categories";
    private static String BearerToken;

    public CategoriesAndParametersScenarios(String bearerToken)
    {
        BearerToken = bearerToken;
    }

    private String generateRandomCategoryId()
    {
        return UUID.randomUUID().toString();
    }

    public Response verifyGettingMainCategories()
    {
        Response response = GetRequestHandler.sendGetRequest(BearerToken, BaseGetRequest, 200);

        List<String> foundCategories = response.jsonPath().getList("categories.name");
        Assert.assertEquals(foundCategories, MainCategories.ListOfCategories);

        return response;
    }

    public void verifyGettingChildCategories()
    {
        Response mainCategoriesRequestResponse = verifyGettingMainCategories();

        for(int i = 0; i < mainCategoriesRequestResponse.jsonPath().getList("categories.id").size(); i++)
        {
            String categoryId = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.id[%d]", i));
            String getRequest = String.format("%s?parent.id=%s", BaseGetRequest, categoryId);

            Response response = GetRequestHandler.sendGetRequest(BearerToken, getRequest, 200);
            Assert.assertTrue(response.jsonPath().getList("categories.id").size() > 0);
        }
    }

    public void verifyGettingCategoryWithNotExistingParent()
    {
        String categoryId = generateRandomCategoryId();
        String getRequest = String.format("%s?parent.id=%s", BaseGetRequest, categoryId);

        GetRequestHandler.sendGetRequest(BearerToken, getRequest, 404);
    }

    public void verifyGettingCategoryById()
    {
        Response mainCategoriesRequestResponse = verifyGettingMainCategories();

        for(int i = 0; i < mainCategoriesRequestResponse.jsonPath().getList("categories.id").size(); i++)
        {
            String categoryId = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.id[%d]", i));
            String categoryName = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.name[%d]", i));

            String getRequest = String.format("%s/%s", BaseGetRequest, categoryId);
            Response response = GetRequestHandler.sendGetRequest(BearerToken, getRequest, 200);
            Assert.assertEquals(response.jsonPath().getString("name"), categoryName);
        }
    }

    public void verifyGettingNotExistingCategory()
    {
        String categoryId = generateRandomCategoryId();
        String getRequest = String.format("%s/%s", BaseGetRequest, categoryId);

        GetRequestHandler.sendGetRequest(BearerToken, getRequest, 404);
    }

    public void verifyGettingCategoryParameters()
    {
        Response mainCategoriesRequestResponse = verifyGettingMainCategories();

        for(int i = 0; i < mainCategoriesRequestResponse.jsonPath().getList("categories.id").size(); i++)
        {
            String categoryId = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.id[%d]", i));
            String getRequest = String.format("%s/%s/parameters", BaseGetRequest, categoryId);
            Response response = GetRequestHandler.sendGetRequest(BearerToken, getRequest, 200);
            Assert.assertFalse(response.body().asString().isEmpty());
        }
    }

    public void verifyGettingNotExistingCategoryParameters()
    {
        String categoryId = generateRandomCategoryId();
        String getRequest = String.format("%s/%s/parameters", BaseGetRequest, categoryId);

        GetRequestHandler.sendGetRequest(BearerToken, getRequest, 404);
    }

    private HashMap<String, String> getResponse(String request)
    {
        Response mainCategoriesRequestResponse = verifyGettingMainCategories();
        HashMap<String, String> categoryInfo = new HashMap<String, String>();

        for(int i = 0; i < mainCategoriesRequestResponse.jsonPath().getList("categories.id").size(); i++)
        {
            String categoryId = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.id[%d]", i));
            String categoryName = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.name[%d]", i));
            categoryInfo.put(categoryName, categoryId);
        }

        return categoryInfo;
    }
}
