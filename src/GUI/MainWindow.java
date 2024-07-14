package GUI;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MainWindow extends JFrame {
    private final JComboBox<String> categorySelector;
    private final JTable productTable;
    private JButton viewCartButton;
    private final JButton addToCartButton;
    private final ShoppingCart cart;
    private final JLabel productIdLabel;
    private final JLabel categoryNameLabel;
    private final JLabel nameLabel;
    private final JLabel sizeLabel;
    private final JLabel colorLabel;
    private final JLabel itemsAvailableLabel;
    private final JLabel attribute1Label;
    private final JLabel attribute2Label;
    private final JLabel attribute1NameLabel;
    private final JLabel attribute2NameLabel;

    private final WestminsterShoppingManager westminsterShoppingManager;

    public MainWindow(ShoppingCart cart, WestminsterShoppingManager westminsterShoppingManager) {
        super("Westminster Shopping Centre");
        this.cart = cart;
        this.westminsterShoppingManager = westminsterShoppingManager;

        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        categorySelector = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        JLabel categoryLabel = new JLabel("Select Product Category");
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categorySelector);
        viewCartButton = new JButton("View Shopping Cart");
        JPanel topPanel = new JPanel();
        topPanel.add(categoryLabel);
        topPanel.add(categorySelector);
        viewCartButton = new JButton("View Shopping Cart");
        topPanel.add(Box.createRigidArea(new Dimension(200, 0)));
        topPanel.add(viewCartButton);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        Object[][] data = {};
        productTable = new JTable(data, columnNames);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        int top = 10, left = 30, bottom = 20, right = 30;
        tablePanel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        add(tablePanel, BorderLayout.CENTER);

        addToCartButton = new JButton("Add to Shopping Cart");
        add(addToCartButton, BorderLayout.SOUTH);

        productTable.setAutoCreateRowSorter(true);

        productIdLabel = new JLabel();
        categoryNameLabel = new JLabel();
        nameLabel = new JLabel();
        sizeLabel = new JLabel();
        colorLabel = new JLabel();
        itemsAvailableLabel = new JLabel();
        attribute1Label = new JLabel();
        attribute2Label = new JLabel();

        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(productIdLabel);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryNameLabel);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameLabel);

        formPanel.add(new JLabel("Items Available:"));
        formPanel.add(itemsAvailableLabel);

        attribute1NameLabel = new JLabel("Attribute 1:");
        attribute2NameLabel = new JLabel("Attribute 2:");
        formPanel.add(attribute1NameLabel);
        formPanel.add(attribute1Label);
        formPanel.add(attribute2NameLabel);
        formPanel.add(attribute2Label);

        add(formPanel, BorderLayout.SOUTH);

        JLabel detailsLabel = new JLabel("Selected Product - Details");
        JButton addToCartButton = new JButton("Add to Cart");

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);
        detailsPanel.add(formPanel, BorderLayout.CENTER);
        detailsPanel.add(addToCartButton, BorderLayout.SOUTH);

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

    private void updateAttributeLabels(Product selectedProduct) {
    if (selectedProduct instanceof Clothing) {
        attribute1NameLabel.setText("Size:");
        attribute2NameLabel.setText("Color:");
        attribute1Label.setText(((Clothing) selectedProduct).getSize());
        attribute2Label.setText(((Clothing) selectedProduct).getColor());
    } else if (selectedProduct instanceof Electronics) {
        attribute1NameLabel.setText("Brand:");
        attribute2NameLabel.setText("Warranty Period:");
        attribute1Label.setText(((Electronics) selectedProduct).getBrand());
        attribute2Label.setText(((Electronics) selectedProduct).getWarrantyPeriod() + " years");
    } else {
        attribute1NameLabel.setText("N/A");
        attribute2NameLabel.setText("N/A");
        attribute1Label.setText("");
        attribute2Label.setText("");
    }
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

                    // Update the attribute labels based on the product category
                    updateAttributeLabels(selectedProduct);
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
