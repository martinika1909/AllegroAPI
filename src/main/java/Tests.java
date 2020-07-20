import org.junit.Before;
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
        categoriesAndParametersScenarios.verifyGettingChildCategories();
    }

    @Test
    public void verifyNotExistingParentCategory()
    {
        categoriesAndParametersScenarios.verifyGettingCategoryWithNotExistingParent();
    }

    @Test
    public void verifyCategoryById()
    {
        categoriesAndParametersScenarios.verifyGettingCategoryById();
    }

    @Test
    public void verifyNotExistingCategory()
    {
        categoriesAndParametersScenarios.verifyGettingNotExistingCategory();
    }

    @Test
    public void verifyGettingCategoryParameters()
    {
        categoriesAndParametersScenarios.verifyGettingCategoryParameters();
    }

    @Test
    public void verifyNotExistingCategoryParameters()
    {
        categoriesAndParametersScenarios.verifyGettingNotExistingCategoryParameters();
    }
}
