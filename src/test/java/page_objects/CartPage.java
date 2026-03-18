package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage{
    public CartPage(){
        super();
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "div.cart_item")
    public List<WebElement> cartItems;
    By cartItemName = By.cssSelector("div.inventory_item_name");
    @FindBy(css = "button#checkout")
    public WebElement checkoutButton;

    public List<String> getItemNamesInCart(){
        List<String> itemNames = cartItems.stream()
                .map(el -> el.findElement(cartItemName)
                        .getText()
                        .trim())
                .collect(Collectors.toList());
        return itemNames;
    }
    public boolean verifyCartContainsItems(List<String> items){
        boolean itemFound = false;
        for(String elm: items){
            if(getItemNamesInCart().contains(elm)){
                itemFound = true;
            }else{
                return false;
            }
        }
        return itemFound;
    }
    public void removeItemsFromCart(List<String> itemNames){
        for(WebElement itemcard: cartItems){
            if(itemNames.contains(itemcard.findElement(cartItemName).getText())){
                click(itemcard.findElement(By.tagName("button")));
            }
        }

    }
    public void openCheckoutPage(){
        click(checkoutButton);
        waitForPageLoad();
    }

}
