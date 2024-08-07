package core;

import GUI.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {
    private final List<Product> products;
    private boolean isFirstPurchase;
    private User user;

    public ShoppingCart(User user) {
        this.products = new ArrayList<>();
        this.user = user;
        this.isFirstPurchase = !user.isFirstOrderDone();
    }

    // Add products to the cart
    public void addProduct(Product product) {
        products.add(product);
        if (!user.isFirstOrderDone()) {
            user.setFirstOrderDone(true);
            saveUserData();
        }
    }

    // Calculate the total price of the products in the cart
    public double calculateTotalWithoutDiscount() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    // Calculate the discount for the first purchase
    public double calculateFirstPurchaseDiscount() {
        if (isFirstPurchase) {
            return calculateTotalWithoutDiscount() * 0.1; // 10% discount
        }
        return 0;
    }

    // Calculate the discount for having three or more items in the same category
    public double calculateCategoryDiscount() {
        Map<String, Long> categoryCounts = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        for (Long count : categoryCounts.values()) {
            if (count >= 3) {
                return calculateTotalWithoutDiscount() * 0.2; // 20% discount
            }
        }
        return 0;
    }

    // Calculate the final total price after applying all discounts
    public double calculateFinalTotal() {
        double total = calculateTotalWithoutDiscount();
        if (!user.isFirstOrderDone()) {
            total -= calculateFirstPurchaseDiscount();
            user.setFirstOrderDone(true);
        }
        total -= calculateCategoryDiscount();
        return total;
    }

    // Get the data for the cart table
    private Map<String, Integer> getCategoryCounts() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Product product : products) {
            String category = product.getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }
        return categoryCounts;
    }

    // Get the items in the cart with their quantities
    public Map<Product, Integer> getItems() {
        Map<Product, Integer> itemsWithQuantities = new HashMap<>();
        for (Product product : products) {
            itemsWithQuantities.put(product, itemsWithQuantities.getOrDefault(product, 0) + 1);
        }
        return itemsWithQuantities;
    }

    private void saveUserData() {
        UserManager userManager = new UserManager();
        userManager.updateUser(this.user);
    }
}
