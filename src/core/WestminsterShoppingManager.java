package core;

import java.util.HashMap;
import java.util.Map;

public class WestminsterShoppingManager implements ShoppingManager {
    private Map<String, ShoppingCart> userCarts;
    private Map<String, String> userSessions;

    public WestminsterShoppingManager() {
        this.userCarts = new HashMap<>();
        this.userSessions = new HashMap<>();
    }

    @Override
    public void addCart(ShoppingCart cart) {
        // Implementation code here
    }

    @Override
    public void removeCart(ShoppingCart cart) {
        // Implementation code here
    }

    @Override
    public ShoppingCart getCartByUserId(String userId) {
        // Implementation code here, returning the shopping cart for the given user ID
        return null;
    }

    @Override
    public void loginUser(String userId) {
        // Implementation code here, handling user login
    }

    @Override
    public void logoutUser(String userId) {
        // Implementation code here, handling user logout
    }
}
