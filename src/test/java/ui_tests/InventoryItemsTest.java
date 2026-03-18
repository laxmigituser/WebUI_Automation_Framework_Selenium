package ui_tests;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import page_objects.PageObjectManager;
import utils.JsonUtils;

import java.util.stream.Collectors;

public class InventoryItemsTest extends TestBase {
    @Test(description = "Verify number of products in inventory", groups = {"regression", "sanity"})
    public void productCountVerificationInventory(){
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        Assert.assertEquals(pom.inventoryPage().getItemsCount(), 7, "Items count not matching in Inventory page");
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();
    }

    @Test(description = "Item list sort test", groups = {"regression"})
    public void productNameSortTest() throws InterruptedException {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        pom.inventoryPage().selectFromFilterDropdown("Name (A to Z)");
        Assert.assertEquals(pom.inventoryPage().getItemNamesList(),
        pom.inventoryPage().getItemNamesList().stream().sorted().collect(Collectors.toList()),
        "List is not sorted");
        Thread.sleep(2000);
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();
    }
    @Test(description = "Item list price sort test", groups = {"sanity"})
    public void productPriceSortTest() throws InterruptedException {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        pom.inventoryPage().selectFromFilterDropdown("Price (low to high)");
        Assert.assertEquals(pom.inventoryPage().getItemPriceList(),
                pom.inventoryPage().getItemPriceList().stream().sorted().collect(Collectors.toList()),
                "List is not sorted as per price");
        Thread.sleep(3000);
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();
    }
}
