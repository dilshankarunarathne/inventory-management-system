package GUI;

import core.Product;
import core.ShoppingCart;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ShoppingCartWindow extends JFrame {
    private final JTable cartTable;
    private final JLabel totalLabel;
    private final JLabel firstPurchaseDiscountLabel;
    private final JLabel categoryDiscountLabel;
    private final JLabel finalTotalLabel;
    private final ShoppingCart cart;

    public ShoppingCartWindow(ShoppingCart cart) {
        super("Shopping Cart");
        this.cart = cart;
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Overall padding

        // Cart Table with padding
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the table
        String[] columnNames = {"Product", "Quantity", "Price"};
        Object[][] data = getCartData();
        cartTable = new JTable(data, columnNames);
        tablePanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Total and Discounts Panel with padding
        JPanel totalsWrapperPanel = new JPanel(new BorderLayout());
        totalsWrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the totals
        JPanel totalsPanel = new JPanel(new GridLayout(4, 2));

        JLabel totalTextLabel = new JLabel("Total:");
        totalTextLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalsPanel.add(totalTextLabel);
        totalLabel = new JLabel(String.format("%.2f", cart.calculateTotalWithoutDiscount()));
        totalLabel.setHorizontalAlignment(JLabel.LEFT);
        totalsPanel.add(totalLabel);

        JLabel firstPurchaseDiscountTextLabel = new JLabel("First Purchase Discount:");
        firstPurchaseDiscountTextLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalsPanel.add(firstPurchaseDiscountTextLabel);
        firstPurchaseDiscountLabel = new JLabel(String.format("%.2f", cart.calculateFirstPurchaseDiscount()));
        firstPurchaseDiscountLabel.setHorizontalAlignment(JLabel.LEFT);
        totalsPanel.add(firstPurchaseDiscountLabel);

        JLabel categoryDiscountTextLabel = new JLabel("Category Discount:");
        categoryDiscountTextLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalsPanel.add(categoryDiscountTextLabel);
        categoryDiscountLabel = new JLabel(String.format("%.2f", cart.calculateCategoryDiscount()));
        categoryDiscountLabel.setHorizontalAlignment(JLabel.LEFT);
        totalsPanel.add(categoryDiscountLabel);

        JLabel finalTotalTextLabel = new JLabel("Final Total:");
        finalTotalTextLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalsPanel.add(finalTotalTextLabel);
        finalTotalLabel = new JLabel(String.format("%.2f", cart.calculateFinalTotal()));
        finalTotalLabel.setHorizontalAlignment(JLabel.LEFT);
        totalsPanel.add(finalTotalLabel);

        totalsWrapperPanel.add(totalsPanel, BorderLayout.CENTER);

        // Adding wrapped components to main panel
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(totalsWrapperPanel, BorderLayout.SOUTH);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private Object[][] getCartData() {
        Map<Product, Integer> items = cart.getItems();
        Object[][] data = new Object[items.size()][3];
        int i = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            data[i][0] = entry.getKey().getProductName();
            data[i][1] = entry.getValue();
            data[i][2] = entry.getKey().getPrice();
            i++;
        }
        return data;
    }


    // Additional methods for updating cart and totals
}
