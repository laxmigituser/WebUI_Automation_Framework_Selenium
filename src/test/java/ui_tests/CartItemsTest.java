package ui_tests;

import base.TestBase;
import database.DBService;
import org.testng.Assert;
import org.testng.annotations.Test;
import page_objects.*;
import reports.ExtentFactory;
import utils.CaptureNetwork;
import utils.JsonUtils;
import utils.NetworkInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartItemsTest extends TestBase {
    @Test(description = "Verify item gets added to cart", groups = { "sanity"})
    public void productCountVerification() throws InterruptedException {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");
        List<String> itemList = JsonUtils.getList("product_details.items");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        pom.inventoryPage().addItemsToCart(itemList);
        Assert.assertEquals(pom.inventoryPage().getItemCountOnCartIcon(), 3, "Item count on cart icon is not matching");
        pom.inventoryPage().openCart();
        Assert.assertTrue(pom.cartPage().verifyCartContainsItems(itemList),"Items not found in cart");
        Thread.sleep(4000);
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();
    }

    @Test(description = "Verify item gets removed from cart", groups = {"sanity"})
    public void removeItemInCartTest() throws InterruptedException {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");
        List<String> itemList = JsonUtils.getList("product_details.items");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        pom.inventoryPage().addItemsToCart(itemList);
        pom.inventoryPage().openCart();

        pom.cartPage().removeItemsFromCart(Arrays.asList("Sauce Labs Backpack"));
        Assert.assertFalse(pom.cartPage().verifyCartContainsItems(Arrays.asList("Sauce Labs Backpack")),
                "Items found in cart");
        Assert.assertEquals(pom.inventoryPage().getItemCountOnCartIcon(), 1, "Item count on cart icon is not matching");
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();
    }
    //checkout flow
    @Test(description = "purchase items e2e flow",groups = {"regression", "sanity"})
    //@CaptureNetwork // for network call capture
    public void completeOrderE2EFlow() throws Exception {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");
        List<String> itemList = JsonUtils.getList("product_details.items");
        String checkout_fname = JsonUtils.getValue("product_details.checkout_details.fname");
        String checkout_lname = JsonUtils.getValue("product_details.checkout_details.lname");
        String checkout_zip = JsonUtils.getValue("product_details.checkout_details.zipcode");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        pom.inventoryPage().addItemsToCart(itemList);
        pom.inventoryPage().openCart();
        pom.cartPage().openCheckoutPage();
        pom.checkoutPage().enterCheckoutDetails(Optional.of(checkout_fname), checkout_lname, checkout_zip);
        Assert.assertEquals(pom.checkoutPage().getItemsInCheckOutOverview(), itemList, "Items not matching");
        pom.checkoutPage().clickFinish();
        pom.checkoutCompletePage().verifyOrderCompleteDetails();
        Thread.sleep(4000);
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();

        //Network call
        //String response = NetworkInterceptor.getResponse("/orders");
        //ExtentFactory.getInstance()
        //        .getExtent()
        //        .info("API Response: " + response);

        //DB validation
        List<Map<String, Object>> rows =
                DBService.query(
                        "mysql",
                        "demodb2",
                        "select * from category_table");

        System.out.println(rows);
        ExtentFactory.getInstance()
                .getExtent()
                .info("DB Result: " + rows.toString());

    }
    //negative checkout flow
    @Test(description = "purchase items e2e flow with incomplete details")
    public void checkoutWithIncompletedataTest() throws InterruptedException {
        PageObjectManager pom = PageObjectManager.get();
        String username = JsonUtils.getValue("userdata.validuser.username");
        String password = JsonUtils.getValue("userdata.validuser.password");
        List<String> itemList = JsonUtils.getList("product_details.items");
        String checkout_lname = JsonUtils.getValue("product_details.checkout_details.lname");
        String checkout_zip = JsonUtils.getValue("product_details.checkout_details.zipcode");
        String checkout_error = JsonUtils.getValue("product_details.checkout_details.error_text");

        pom.loginPage().navigateTLoginPage();
        pom.loginPage().loginToApplication(username, password);
        pom.inventoryPage().addItemsToCart(itemList);
        pom.inventoryPage().openCart();
        pom.cartPage().openCheckoutPage();
        pom.checkoutPage().enterCheckoutDetails(Optional.empty(), checkout_lname, checkout_zip);
        Assert.assertEquals(pom.checkoutPage().getErrorText(), checkout_error,"Login error text is not matching");
        Thread.sleep(4000);
        pom.inventoryPage().clickHamburger();
        pom.inventoryPage().logout();

    }



}
