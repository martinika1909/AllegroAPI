import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Tests
{
    private static CategoriesAndParametersScenarios categoriesAndParametersScenarios;

    @BeforeClass
    public static void setUp()
    {
        categoriesAndParametersScenarios = new CategoriesAndParametersScenarios(Authentication.getBearerToken());
    }

    @Test
    public void verifyMainCategories()
    {
        categoriesAndParametersScenarios.verifyGettingMainCategories();
    }

    @Test
    public void verifyChildCategories()
    {
        categoriesAndParametersScenarios.sendGetRequest(CategoriesAndParametersScenarios.ChildCategoryByParentCategory);
    }

    @Test
    public void verifyNotExistingParentCategory()
    {
        categoriesAndParametersScenarios.sendGetRequestNegativeCase(CategoriesAndParametersScenarios.ChildCategoryByParentCategory);
    }

    @Test
    public void verifyCategoryById()
    {
        categoriesAndParametersScenarios.sendGetRequest(CategoriesAndParametersScenarios.CategoryById);
    }

    @Test
    public void verifyNotExistingCategory()
    {
        categoriesAndParametersScenarios.sendGetRequestNegativeCase(CategoriesAndParametersScenarios.CategoryById);
    }

    @Test
    public void verifyGettingCategoryParameters()
    {
        categoriesAndParametersScenarios.sendGetRequest(CategoriesAndParametersScenarios.CategoryParameters);
    }

    @Test
    public void verifyNotExistingCategoryParameters()
    {
        categoriesAndParametersScenarios.sendGetRequestNegativeCase(CategoriesAndParametersScenarios.CategoryParameters);
    }
}
