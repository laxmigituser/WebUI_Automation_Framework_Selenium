package ui_tests;

import base.TestBase;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_objects.PageObjectManager;
import utils.ExcelUtils;
import utils.JsonUtils;

import java.util.List;
import java.util.Map;

public class UserLoginLogOutTest extends TestBase {

    @Test(groups = {"sanity"},description = "Valid User login test")
    public void loginLogoutFlow() throws InterruptedException {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        String pageUrl = pom.loginPage().getCurrentPageUrl();
        Assert.assertTrue(pageUrl.contains("/inventory.html"), "page url does not include Inventory");
        pom.inventoryPage().logout();
        Assert.assertTrue(pom.inventoryPage().getCurrentPageUrl().contains("saucedemo.com/"), "page url does not include login");
    }

    @Test(description = "Locked out user login test")
    public void lockedUserLogin(){
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.lockeduser.username");
        String password = JsonUtils.getValue("userdata.lockeduser.password");
        String error_text = JsonUtils.getValue("userdata.lockeduser.login_error_text");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        Assert.assertTrue(pom.loginPage().isErrorVisible(), "Error message is not visible on page");
        Assert.assertEquals(pom.loginPage().getLoginErrorText(), error_text,"login error text is not matching");
        Assert.assertTrue(pom.loginPage().getCurrentPageUrl().contains("saucedemo.com/"), "page url does not include login");
    }

    @Test(description = "Invalid user login test", groups = {"regression"})
    public void invalidUserLogin(){
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.invaliduser.username");
        String password = JsonUtils.getValue("userdata.invaliduser.password");
        String error_text = JsonUtils.getValue("userdata.invaliduser.login_error_text");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        Assert.assertTrue(pom.loginPage().isErrorVisible(), "Error message is not visible on page");
        Assert.assertEquals(pom.loginPage().getLoginErrorText(), error_text,"login error text is not matching");
        Assert.assertTrue(pom.loginPage().getCurrentPageUrl().contains("saucedemo.com/"), "page url does not include login");
    }

    @DataProvider(name = "invalidUserData", parallel = true)
    public Object[][] invalidUserDataProvider() {
        return JsonUtils.getDataProviderData("userdata.invalid_users");
    }
    @Test(dataProvider = "invalidUserData")
    public void invalidUsersLoginTest(JsonNode user){
        PageObjectManager pom = PageObjectManager.get();
        String username = user.get("username").asText();
        String password = user.get("password").asText();
        String error_text = user.get("login_error_text").asText();

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        Assert.assertTrue(pom.loginPage().isErrorVisible(), "Error message is not visible on page");
        Assert.assertEquals(pom.loginPage().getLoginErrorText(), error_text,"login error text is not matching");
        Assert.assertTrue(pom.loginPage().getCurrentPageUrl().contains("saucedemo.com/"), "page url does not include login");
    }

    @DataProvider(name = "validUsersData", parallel = true)
    public Object[][] validUserDataProvider(){
        String path = "src/test/resources/testdata/users.xlsx";
        List<Map<String, String>> data = ExcelUtils.getData(path, "Sheet1");
        Object[][] dp = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            dp[i][0] = data.get(i);
        }
        return dp;
    }
    @Test(dataProvider = "validUsersData", groups = {"regression", "sanity"})
    public void verifyValidUsersLogin(Map<String, String> data){
        PageObjectManager pom = PageObjectManager.get();

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(data.get("username"), data.get("password"));
        String pageUrl = pom.loginPage().getCurrentPageUrl();
        Assert.assertTrue(pageUrl.contains("/inventory.html"), "page url does not include Inventory");
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();
        Assert.assertTrue(pom.inventoryPage().getCurrentPageUrl().contains("saucedemo.com/"), "page url does not include login");

    }


}
