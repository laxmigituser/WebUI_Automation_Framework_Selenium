package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage{
    public InventoryPage(){
        super();
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "button#react-burger-menu-btn")
    public WebElement hamburger;
    @FindBy(css = "div.bm-menu-wrap")
    public WebElement sidePanel;
    @FindBy(css = "a#logout_sidebar_link")
    public WebElement logoutButton;
    @FindBy(css = "select.product_sort_container")
    public WebElement filterDropdown;
    @FindBy(css = "div.inventory_list")
    public WebElement itemList;
    By items = By.cssSelector("div.inventory_item");
    By itemNames = By.cssSelector("div.inventory_item_name ");
    By itemPrices = By.cssSelector("div.inventory_item_price ");
    @FindBy(css = "a.shopping_cart_link")
    public WebElement cartIconWithItemcount;

    public boolean verifySidePanelOpened(){
        elementVisible(sidePanel);
        return sidePanel.getDomAttribute("aria-hidden").equals(false);
    }
    public void clickHamburger(){
        click(hamburger);
    }
    public void logout(){
        while(verifySidePanelOpened()){
            clickUsingJS(hamburger);
        }
//        verifySidePanelOpened();
        click(logoutButton);
        waitForPageLoad();
    }
    public int getItemsCount(){
        return itemList.findElements(items).size();
    }
    public List<String> getItemNamesList(){
        List<String> itemNamesList = itemList.findElements(items).stream().map(elm-> elm.findElement(itemNames).getText())
                                                .collect(Collectors.toList());
        return itemNamesList;
    }
    public List<Double> getItemPriceList(){
        List<Double> itemPriceList = itemList.findElements(items).stream()
                            .map(elm-> Double.valueOf(elm.findElement(itemPrices).getText()
                             .replace("$","").trim()))
                .collect(Collectors.toList());
        return itemPriceList;
    }
    public void selectFromFilterDropdown(String option){
        Select select = new Select(filterDropdown);
        select.selectByVisibleText(option);
        waitForPageLoad();
    }

    public void addItemsToCart(List<String> itemNames){
        List<WebElement> items_list = driver.findElements(items);
        for(WebElement elm: items_list){
            String item_name = elm.findElement(By.cssSelector("div.inventory_item_name")).getText();
            if(itemNames.contains(item_name)){
                elm.findElement(By.tagName("button")).click();
            }
        }
    }
    public int getItemCountOnCartIcon(){
        return Integer.valueOf(cartIconWithItemcount.findElement(By.tagName("span")).getText());
    }
    public void openCart(){
        cartIconWithItemcount.click();
        waitForPageLoad();
    }
}
