package core;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private final int MAX_PRODUCTS = 50;
    private final String PRODUCTS_FILE = "products.dat";
    private Map<String, Product> products = new HashMap<>();

    public WestminsterShoppingManager() {
        loadProducts();
    }

    // Display the management menu
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n--- Management Menu ---");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addProduct(scanner);
                    break;
                case "2":
                    deleteProduct(scanner);
                    break;
                case "3":
                    printProducts();
                    break;
                case "4":
                    saveProducts();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (!choice.equals("5"));
    }

    // Add a new product to the system
    private void addProduct(Scanner scanner) {
        if (products.size() >= MAX_PRODUCTS) {
            System.out.println("Cannot add more products. The system is at its maximum capacity.");
            return;
        }

        System.out.print("Enter product type (Electronics/Clothing): ");
        String type = scanner.nextLine();
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter available items: ");
        int availableItems = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Product product = null;
        if ("Electronics".equalsIgnoreCase(type)) {
            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();
            System.out.print("Enter warranty period: ");
            String warrantyPeriod = scanner.nextLine();
            product = new Electronics(productId, productName, availableItems, price, brand, Integer.parseInt(warrantyPeriod));
        } else if ("Clothing".equalsIgnoreCase(type)) {
            System.out.print("Enter size: ");
            String size = scanner.nextLine();
            System.out.print("Enter color: ");
            String color = scanner.nextLine();
            product = new Clothing(productId, productName, availableItems, price, size, color);
        }

        if (product != null) {
            products.put(productId, product);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Product type is not recognized.");
        }
    }

    public void deleteProduct(Scanner scanner) {
        System.out.print("Enter the product ID to delete: ");
        String productId = scanner.nextLine();
        if (products.remove(productId) != null) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("No product found with ID: " + productId);
        }
    }

    public void printProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\nList of Products:");
        for (Product product : products.values()) {
            System.out.println(product);
        }
    }

    public void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
            System.out.println("Products saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadProducts() {
        File file = new File(PRODUCTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (Map<String, Product>) ois.readObject();
                System.out.println("Products loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading products: " + e.getMessage());
            }
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
