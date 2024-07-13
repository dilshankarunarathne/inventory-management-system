package GUI;

import core.Product;
import core.ShoppingCart;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ShoppingCartWindow extends JFrame {
    private JTable cartTable;
    private JLabel totalLabel, discountLabel, finalTotalLabel;
    private ShoppingCart cart;

    public ShoppingCartWindow(ShoppingCart cart) {
        super("Shopping Cart");
        this.cart = cart;
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Cart Table
        String[] columnNames = {"Product", "Quantity", "Price"};
        Object[][] data = getCartData(); // Method call to populate data
        cartTable = new JTable(data, columnNames);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Total and Discounts
        JPanel totalsPanel = new JPanel(new GridLayout(3, 2));
        totalsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel(String.valueOf(cart.calculateTotalWithoutDiscount()));
        totalsPanel.add(totalLabel);

        totalsPanel.add(new JLabel("Discounts:"));
        discountLabel = new JLabel(String.valueOf(cart.calculateDiscounts()));
        totalsPanel.add(discountLabel);

        totalsPanel.add(new JLabel("Final Total:"));
        finalTotalLabel = new JLabel(String.valueOf(cart.calculateFinalTotal()));
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
