package GUI;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MainWindow extends JFrame {
    private JComboBox<String> categorySelector;
    private JTable productTable;
    private JButton viewCartButton, addToCartButton;
    private ShoppingCart cart;
    private JLabel productIdLabel, categoryNameLabel, nameLabel, sizeLabel, colorLabel, itemsAvailableLabel;
    // Declaration of dynamic attribute labels
    private JLabel attribute1Label, attribute2Label;

    private WestminsterShoppingManager westminsterShoppingManager;

    public MainWindow(ShoppingCart cart, WestminsterShoppingManager westminsterShoppingManager) {
        super("Westminster Shopping Centre");
        this.cart = cart;
        this.westminsterShoppingManager = westminsterShoppingManager;

        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Category Selector
        categorySelector = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});

        // Product Table
        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        Object[][] data = {}; // Populate with product data
        productTable = new JTable(data, columnNames);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JLabel categoryLabel = new JLabel("Select Product Category");

        // Step 2: Add the label and categorySelector to a panel for proper alignment
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categorySelector);

        // Add to Cart Button
        addToCartButton = new JButton("Add to Shopping Cart");
        add(addToCartButton, BorderLayout.SOUTH);

        // View Cart Button
        viewCartButton = new JButton("View Shopping Cart");

        productTable.setAutoCreateRowSorter(true);

        // Create a new JPanel to hold the label, category selector, and view cart button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add the category label and category selector to the panel
        topPanel.add(categoryLabel);
        topPanel.add(categorySelector);

        // Initialize the viewCartButton if not already done
        viewCartButton = new JButton("View Shopping Cart");

        // Add the viewCartButton to the panel
        topPanel.add(viewCartButton);

        // Add the topPanel to the JFrame
        add(topPanel, BorderLayout.NORTH);

        productIdLabel = new JLabel();
        categoryNameLabel = new JLabel();
        nameLabel = new JLabel();
        sizeLabel = new JLabel();
        colorLabel = new JLabel();
        itemsAvailableLabel = new JLabel();
        attribute1Label = new JLabel();
        attribute2Label = new JLabel();

        // Add a panel for the form
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(productIdLabel);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryNameLabel);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameLabel);
        formPanel.add(new JLabel("Attribute 1:")); // Generic label, will be dynamically updated
        formPanel.add(attribute1Label);
        formPanel.add(new JLabel("Attribute 2:")); // Generic label, will be dynamically updated
        formPanel.add(attribute2Label);
        formPanel.add(new JLabel("Items Available:"));
        formPanel.add(itemsAvailableLabel);

        // Add the formPanel to the JFrame
        add(formPanel, BorderLayout.SOUTH);

        // Step 1: Create the label and button
        JLabel detailsLabel = new JLabel("Selected Product - Details");
        JButton addToCartButton = new JButton("Add to Cart");

        // Step 2 & 3 & 4: Create a new panel for the label, form, and button
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);
        detailsPanel.add(formPanel, BorderLayout.CENTER); // Assuming formPanel is your existing form panel
        detailsPanel.add(addToCartButton, BorderLayout.SOUTH);

        // Step 5: Add the detailsPanel to the MainWindow
        // Assuming you want to replace the existing formPanel in the SOUTH of the MainWindow
        add(detailsPanel, BorderLayout.SOUTH);

        categorySelector.addActionListener(e -> {
            String selectedCategory = (String) categorySelector.getSelectedItem();
            List<Product> filteredProducts;
            if ("All".equals(selectedCategory)) {
                filteredProducts = getAllProducts();
            } else {
                // todo simplify this logic
                filteredProducts = getAllProducts().stream()
                        .filter(product -> product instanceof Clothing && "Clothing".equals(selectedCategory)
                                || product instanceof Electronics && "Electronics".equals(selectedCategory))
                        .collect(Collectors.toList());
            }
            updateProductTable(filteredProducts);
        });

        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                String productId = (String) productTable.getValueAt(selectedRow, 0);
                addProductToCart(productId);
            } else {
                JOptionPane.showMessageDialog(this, "No product selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(productTable.getModel());
        productTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // Column index 1 for "Name"
        sorter.setSortKeys(sortKeys);
        sorter.sort();

        // Event Listeners
        setupListeners();
        refreshProductTable();
        updateProductTable(getAllProducts());
    }

    private void addProductToCart(String productId) {
        Product product = findProductById(productId);
        if (product != null) {
            cart.addProduct(product);
            JOptionPane.showMessageDialog(this, "Added to cart: " + product.getProductName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupListeners() {
        // Selecting Products
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                String productId = (String) productTable.getValueAt(productTable.getSelectedRow(), 0);
                Product selectedProduct = findProductById(productId);
                if (selectedProduct != null) {
                    productIdLabel.setText(selectedProduct.getProductId());
                    categoryNameLabel.setText(getCategory(selectedProduct));
                    nameLabel.setText(selectedProduct.getProductName());
                    itemsAvailableLabel.setText(String.valueOf(selectedProduct.getAvailableItems()));

                    // Dynamically update attribute1Label and attribute2Label based on the product category
                    if (selectedProduct instanceof Clothing) {
                        attribute1Label.setText("Size: " + ((Clothing) selectedProduct).getSize());
                        attribute2Label.setText("Color: " + ((Clothing) selectedProduct).getColor());
                    } else if (selectedProduct instanceof Electronics) {
                        attribute1Label.setText("Brand: " + ((Electronics) selectedProduct).getBrand());
                        attribute2Label.setText("Warranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod() + " years");
                    } else {
                        attribute1Label.setText("N/A");
                        attribute2Label.setText("N/A");
                    }
                }
            }
        });

        // Viewing Cart
        viewCartButton.addActionListener(e -> {
            ShoppingCartWindow cartWindow = new ShoppingCartWindow(cart);
            cartWindow.setVisible(true);
        });
    }

    private String getCategory(Product p) {
        if (p instanceof Clothing) {
            return "Clothing";
        } else if (p instanceof Electronics) {
            return "Electronics";
        }
        return "Unknown";
    }

    private void updateProductTable(List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    getCategory(product),
                    product.getPrice(),
                    product.toString() // Assuming toString() method returns the product details
            });
        }

        AvailabilityCellRenderer availabilityRenderer = new AvailabilityCellRenderer(getAllProducts());
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(availabilityRenderer);
        }
    }

    private void refreshProductTable() {
        List<Product> allProducts = getAllProducts();
        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        Object[][] data = new Object[allProducts.size()][columnNames.length];

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductName();
            data[i][2] = getCategory(product);
            data[i][3] = product.getPrice();
            // Update this line to include actual product details instead of just "Details"
            data[i][4] = product.toString(); // Assuming toString() method of Product class returns the details
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

    private List<Product> getAllProducts() {
        return westminsterShoppingManager.getAllProducts();
    }
}
