package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private List<Product> products;
    private boolean isFirstPurchase = true; // Assuming this is the first purchase by default

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public double calculateDiscounts() {
        double discount = 0;
        if (isFirstPurchase) {
            discount += calculateTotalCost() * 0.1; // 10% discount
            isFirstPurchase = false; // Update the flag after applying the first purchase discount
        }
        Map<String, Integer> categoryCounts = getCategoryCounts();
        for (Integer count : categoryCounts.values()) {
            if (count >= 3) {
                discount += calculateTotalCost() * 0.2; // 20% discount for 3 or more items of the same category
                break; // Assuming the discount is applied only once per purchase
            }
        }
        return discount;
    }

    public double calculateFinalTotal() {
        return calculateTotalCost() - calculateDiscounts();
    }

    private Map<String, Integer> getCategoryCounts() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Product product : products) {
            String category = product.getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }
        return categoryCounts;
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
