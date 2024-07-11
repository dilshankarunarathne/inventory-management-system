package core;

public interface ShoppingManager {
    void addCart(ShoppingCart cart);
    void removeCart(ShoppingCart cart);
    ShoppingCart getCartByUserId(String userId);
    void loginUser(String userId);
    void logoutUser(String userId);
}
