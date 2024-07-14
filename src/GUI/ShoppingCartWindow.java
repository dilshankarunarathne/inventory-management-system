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
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Cart Table
        String[] columnNames = {"Product", "Quantity", "Price"};
        Object[][] data = getCartData();
        cartTable = new JTable(data, columnNames);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Total and Discounts Panel
        JPanel totalsPanel = new JPanel(new GridLayout(4, 2));
        totalsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel(String.format("%.2f", cart.calculateTotalWithoutDiscount()));
        totalsPanel.add(totalLabel);

        totalsPanel.add(new JLabel("First Purchase Discount:"));
        firstPurchaseDiscountLabel = new JLabel(String.format("%.2f", cart.calculateFirstPurchaseDiscount()));
        totalsPanel.add(firstPurchaseDiscountLabel);

        totalsPanel.add(new JLabel("Category Discount:"));
        categoryDiscountLabel = new JLabel(String.format("%.2f", cart.calculateCategoryDiscount()));
        totalsPanel.add(categoryDiscountLabel);

        totalsPanel.add(new JLabel("Final Total:"));
        finalTotalLabel = new JLabel(String.format("%.2f", cart.calculateFinalTotal()));
        totalsPanel.add(finalTotalLabel);

        add(totalsPanel, BorderLayout.SOUTH);
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
