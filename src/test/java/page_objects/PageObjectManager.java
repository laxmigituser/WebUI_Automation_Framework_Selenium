package page_objects;

public class PageObjectManager {
    private static ThreadLocal<PageObjectManager> instance = ThreadLocal.withInitial(PageObjectManager::new);

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutCompletePage checkoutCompletePage;

    public static PageObjectManager get(){
        return instance.get();
    }
    public static void remove(){
        instance.remove();
    }

    public LoginPage loginPage(){
        if(loginPage == null){
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public InventoryPage inventoryPage(){
        if(inventoryPage == null){
            inventoryPage = new InventoryPage();
        }
        return inventoryPage;
    }
    public CartPage cartPage(){
        if(cartPage == null){
            cartPage = new CartPage();
        }
        return cartPage;
    }
    public CheckoutPage checkoutPage(){
        if(checkoutPage == null){
            checkoutPage = new CheckoutPage();
        }
        return checkoutPage;
    }
    public CheckoutCompletePage checkoutCompletePage(){
        if(checkoutCompletePage == null){
            checkoutCompletePage = new CheckoutCompletePage();
        }
        return checkoutCompletePage;
    }
}
