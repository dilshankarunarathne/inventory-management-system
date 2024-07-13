package GUI;

import core.Product;
import core.ShoppingCart;
import core.WestminsterShoppingManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import core.Electronics;
import core.Clothing;

import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {
    private JComboBox<String> categorySelector;
    private JTable productTable;
    private JTextArea productDetails;
    private JButton viewCartButton, addToCartButton;
    private ShoppingCart cart;
    private JButton addButton;
    private JComboBox<String> productTypeComboBox;
    private JTextField productIdField, productNameField, availableItemsField, priceField, brandField, warrantyPeriodField, sizeField, colorField;

    private WestminsterShoppingManager westminsterShoppingManager;

    public MainWindow(WestminsterShoppingManager westminsterShoppingManager) {
        super("Westminster Shopping Centre");
        this.cart = cart;
        this.westminsterShoppingManager = westminsterShoppingManager;
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setSize(600, 400);
        setLayout(new FlowLayout());

        productIdField = new JTextField(10);
        productNameField = new JTextField(10);
        availableItemsField = new JTextField(5);
        priceField = new JTextField(5);
        brandField = new JTextField(10);
        warrantyPeriodField = new JTextField(5);
        sizeField = new JTextField(5);
        colorField = new JTextField(10);

        productTypeComboBox = new JComboBox<>(new String[]{"Electronics", "Clothing"});
        addButton = new JButton("Add Product");

        add(new JLabel("Type:"));
        add(productTypeComboBox);
        add(new JLabel("Product ID:"));
        add(productIdField);
        add(new JLabel("Name:"));
        add(productNameField);
        add(new JLabel("Available Items:"));
        add(availableItemsField);
        add(new JLabel("Price:"));
        add(priceField);
        // Add more fields based on the selected product type dynamically if needed

        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addProduct() {
        String type = (String) productTypeComboBox.getSelectedItem();
        String productId = productIdField.getText();
        String productName = productNameField.getText();
        int availableItems = Integer.parseInt(availableItemsField.getText());
        double price = Double.parseDouble(priceField.getText());
        String brandOrSize = type.equals("Electronics") ? brandField.getText() : sizeField.getText();
        String warrantyPeriodOrColor = type.equals("Electronics") ? warrantyPeriodField.getText() : colorField.getText();

        // Now, call the addProduct method with all the required parameters
        westminsterShoppingManager.addProduct(type, productId, productName, availableItems, price, brandOrSize, warrantyPeriodOrColor);
        JOptionPane.showMessageDialog(this, "Product added successfully.");
        // Optionally, refresh the product list display here
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

    private void refreshProductTable() {
        List<Product> allProducts = getAllProducts();
        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        Object[][] data = new Object[allProducts.size()][columnNames.length];

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductName();
            data[i][2] = product.getCategory();
            data[i][3] = product.getPrice();
            data[i][4] = "Details"; // Assuming you have a method to generate details or have a details field
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productTable.setModel(model);
    }

    // Method to find a Product by its ID
    private Product findProductById(String productId) {
        // Assuming getAllProducts() returns a List<Product> of all available products
        for (Product product : getAllProducts()) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null; // or throw an exception if the product is not found
    }

    // Updated addProductToCart method
    private void addProductToCart(String productId) {
        Product product = findProductById(productId);
        if (product != null) {
            cart.addProduct(product);
        } else {
            // Handle the case where the product is not found
            JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Product> getAllProducts() {
        return westminsterShoppingManager.getAllProducts();
    }
}
