package core;

public interface ShoppingManager {
    void addCart(ShoppingCart cart);
    void removeCart(ShoppingCart cart);
    ShoppingCart getCartByUserId(String userId);
    void loginUser(String userId);
    void logoutUser(String userId);
    boolean checkProductAvailability(String productId, int quantity);
    void processPayment(String userId, PaymentDetails paymentDetails);
    void addProduct(Product product);
    void updateProduct(String productId, Product updatedProduct);
    void removeProduct(String productId);
    OrderStatus trackOrder(String orderId);
    boolean handleReturnOrExchange(String orderId, String productId, ReturnOrExchangeDetails details);
}