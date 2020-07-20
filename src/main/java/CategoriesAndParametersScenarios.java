import categories.MainCategories;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;

class CategoriesAndParametersScenarios
{
    private static final String BaseGetRequest = "/sale/categories";
    private static String BearerToken;

    static final String ChildCategoryByParentCategory = "%s?parent.id=%s";
    static final String CategoryById = "%s/%s";
    static final String CategoryParameters = "%s/%s/parameters";

    CategoriesAndParametersScenarios(String bearerToken)
    {
        BearerToken = bearerToken;
    }

    Response verifyGettingMainCategories()
    {
        Response response = GetRequestHandler.sendGetRequest(BearerToken, BaseGetRequest, 200);

        List<String> foundCategories = response.jsonPath().getList("categories.name");
        Assert.assertEquals("Returned categories don't match MainCategories.ListOfCategories", foundCategories, MainCategories.ListOfCategories);
        System.out.println("All main categories found in the response");

        return response;
    }

    void sendGetRequest(String requestTemplate)
    {
        Response mainCategoriesRequestResponse = verifyGettingMainCategories();

        for(int i = 0; i < mainCategoriesRequestResponse.jsonPath().getList("categories.id").size(); i++)
        {
            String categoryId = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.id[%d]", i));
            String categoryName = mainCategoriesRequestResponse.jsonPath().getString(String.format("categories.name[%d]", i));
            String getRequest = String.format(requestTemplate, BaseGetRequest, categoryId);

            Response response = GetRequestHandler.sendGetRequest(BearerToken, getRequest, 200);
            System.out.println(String.format("%s: %s",categoryName, response.body().asString()));
        }
    }

    void sendGetRequestNegativeCase(String requestTemplate)
    {
        String categoryId = UUID.randomUUID().toString();
        String getRequest = String.format(requestTemplate, BaseGetRequest, categoryId);

        Response response = GetRequestHandler.sendGetRequest(BearerToken, getRequest, 404);
        System.out.println(response.body().asString());
    }
}
