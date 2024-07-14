package GUI;

import core.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {
    private final JComboBox<String> categorySelector;
    private final JTable productTable;
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
    private JButton viewCartButton;

    public MainWindow(ShoppingCart cart, WestminsterShoppingManager westminsterShoppingManager) {
        super("Westminster Shopping Centre");
        this.cart = cart;
        this.westminsterShoppingManager = westminsterShoppingManager;

        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top panel
        categorySelector = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        JLabel categoryLabel = new JLabel("Select Product Category");
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categorySelector);
        viewCartButton = new JButton("View Shopping Cart");
        JPanel topPanel = new JPanel();
        topPanel.add(categoryLabel);
        topPanel.add(categorySelector);
        topPanel.add(Box.createRigidArea(new Dimension(200, 0)));
        topPanel.add(viewCartButton);
        add(topPanel, BorderLayout.NORTH);

        // Product table
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

        // Add to cart button
        addToCartButton = new JButton("Add to Shopping Cart");
        add(addToCartButton, BorderLayout.SOUTH);

        productTable.setAutoCreateRowSorter(true);

        // Product details labels
        productIdLabel = new JLabel();
        categoryNameLabel = new JLabel();
        nameLabel = new JLabel();
        sizeLabel = new JLabel();
        colorLabel = new JLabel();
        itemsAvailableLabel = new JLabel();
        attribute1Label = new JLabel();
        attribute2Label = new JLabel();

        // product details panel
        JLabel detailsLabel = new JLabel("Selected Product - Details");
        int topPadding = 0, leftPadding = 50, bottomPadding = 10, rightPadding = 50;
        JPanel detailsPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setPreferredSize(new Dimension(150, 30));
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
        buttonPanel.add(addToCartButton);
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);
        detailsPanel.add(formPanel, BorderLayout.CENTER);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
        add(detailsPanel, BorderLayout.SOUTH);

        // Category selector listener
        categorySelector.addActionListener(e -> {
            String selectedCategory = (String) categorySelector.getSelectedItem();
            List<Product> filteredProducts;
            if ("All".equals(selectedCategory)) {
                filteredProducts = getAllProducts();
            } else {
                filteredProducts = getAllProducts().stream()
                        .filter(product -> product instanceof Clothing && "Clothing".equals(selectedCategory)
                                || product instanceof Electronics && "Electronics".equals(selectedCategory))
                        .collect(Collectors.toList());
            }
            updateProductTable(filteredProducts);
        });

        // Add to cart button
        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                String productId = (String) productTable.getValueAt(selectedRow, 0);
                addProductToCart(productId);
            } else {
                JOptionPane.showMessageDialog(this, "No product selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Table sorting mechanism
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(productTable.getModel());
        productTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // Column index 1 for "Name"
        sorter.setSortKeys(sortKeys);
        sorter.sort();

        setupListeners();
        refreshProductTable();
        updateProductTable(getAllProducts());
    }

    // Update the attribute labels based on the selected product
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
            attribute2Label.setText(((Electronics) selectedProduct).getWarrantyPeriod() + " weeks");
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
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                String productId = (String) productTable.getValueAt(productTable.getSelectedRow(), 0);
                Product selectedProduct = findProductById(productId);
                if (selectedProduct != null) {
                    productIdLabel.setText(selectedProduct.getProductId());
                    categoryNameLabel.setText(getCategory(selectedProduct));
                    nameLabel.setText(selectedProduct.getProductName());
                    itemsAvailableLabel.setText(String.valueOf(selectedProduct.getAvailableItems()));

                    updateAttributeLabels(selectedProduct);
                }
            }
        });

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
        model.setRowCount(0);
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    getCategory(product),
                    product.getPrice(),
                    getDetails(product)
            });
        }

        AvailabilityCellRenderer availabilityRenderer = new AvailabilityCellRenderer(getAllProducts());
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(availabilityRenderer);
        }
    }

    private String getDetails(Product product) {
        if (product instanceof Clothing) {
            return ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor();
        } else if (product instanceof Electronics) {
            return ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " weeks warranty";
        }
        return "No details were found";
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
            data[i][4] = product.toString();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productTable.setModel(model);
    }

    private Product findProductById(String productId) {
        for (Product product : getAllProducts()) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    private List<Product> getAllProducts() {
        return westminsterShoppingManager.getAllProducts();
    }
}
