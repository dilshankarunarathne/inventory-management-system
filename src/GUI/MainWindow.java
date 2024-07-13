package GUI;

import core.ShoppingCart;

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
        // Selecting Products
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                // Assuming product details are in the 4th column (index 3)
                String details = (String) productTable.getValueAt(productTable.getSelectedRow(), 4);
                productDetails.setText(details);
            }
        });

        // Adding to Cart
        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                // Assuming product ID is in the 0th column
                String productId = (String) productTable.getValueAt(selectedRow, 0);
                // This method needs to be implemented to find a Product by its ID and add it to the cart
                addProductToCart(productId);
            }
        });

        // Viewing Cart
        viewCartButton.addActionListener(e -> {
            ShoppingCartWindow cartWindow = new ShoppingCartWindow(cart);
            cartWindow.setVisible(true);
        });
    }

    private void addProductToCart(String productId) {
        // Find the product by productId from your product list or database
        // For example: Product product = findProductById(productId);
        // Then add the product to the cart: cart.addProduct(product);
    }
}
