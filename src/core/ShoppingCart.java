package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {
    private final List<Product> products;
    private boolean isFirstPurchase;

    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.isFirstPurchase = true; // Assuming this is the first purchase by default
    }

    // Add products to the cart
    public void addProduct(Product product) {
        products.add(product);
    }

    // Remove products from the cart
    public void removeProduct(Product product) {
        products.remove(product);
    }

    // Calculate the total price of the products in the cart
    public double calculateTotalWithoutDiscount() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public double calculateFirstPurchaseDiscount() {
        if (isFirstPurchase) {
            return calculateTotalWithoutDiscount() * 0.1; // 10% discount
        }
        return 0;
    }

    public double calculateCategoryDiscount() {
        Map<String, Long> categoryCounts = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        for (Long count : categoryCounts.values()) {
            if (count >= 3) {
                return calculateTotalWithoutDiscount() * 0.2; // 20% discount
            }
        }
        return 0;
    }

    public double calculateFinalTotal() {
        double total = calculateTotalWithoutDiscount();
        total -= calculateFirstPurchaseDiscount();
        total -= calculateCategoryDiscount();
        return total;
    }

    private Map<String, Integer> getCategoryCounts() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Product product : products) {
            String category = product.getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }
        return categoryCounts;
    }

    public Map<Product, Integer> getItems() {
        Map<Product, Integer> itemsWithQuantities = new HashMap<>();
        for (Product product : products) {
            itemsWithQuantities.put(product, itemsWithQuantities.getOrDefault(product, 0) + 1);
        }
        return itemsWithQuantities;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isFirstPurchase() {
        return isFirstPurchase;
    }

    public void setFirstPurchase(boolean isFirstPurchase) {
        this.isFirstPurchase = isFirstPurchase;
    }
}
