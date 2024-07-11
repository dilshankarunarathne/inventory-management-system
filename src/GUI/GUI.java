package GUI;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JComboBox<String> categorySelector;
    private JTable productTable;
    private JTextArea productDetails;
    private JButton viewCartButton, addToCartButton;
    private ShoppingCart cart;

    public MainWindow(ShoppingCart cart) {
        super("Westminster Shopping Centre");
        this.cart = cart;
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Category Selector
        categorySelector = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        add(categorySelector, BorderLayout.NORTH);

        // Product Table
        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        Object[][] data = {}; // Populate with product data
        productTable = new JTable(data, columnNames);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Product Details
        productDetails = new JTextArea(5, 20);
        add(productDetails, BorderLayout.SOUTH);

        // Add to Cart Button
        addToCartButton = new JButton("Add to Shopping Cart");
        add(addToCartButton, BorderLayout.SOUTH);

        // View Cart Button
        viewCartButton = new JButton("View Shopping Cart");
        add(viewCartButton, BorderLayout.EAST);

        // Event Listeners
        setupListeners();
    }

    private void setupListeners() {
        // Implement listeners for selecting products, adding to cart, and viewing cart
    }

    // Additional methods for updating UI based on interactions
}