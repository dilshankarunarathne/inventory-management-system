package GUI;

import javax.swing.*;
import java.awt.*;

public class ShoppingCartWindow extends JFrame {
    private JTable cartTable;
    private JLabel totalLabel, discountLabel, finalTotalLabel;

    public ShoppingCartWindow() {
        super("Shopping Cart");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Cart Table
        String[] columnNames = {"Product", "Quantity", "Price"};
        Object[][] data = {}; // Populate with cart data
        cartTable = new JTable(data, columnNames);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Total and Discounts
        JPanel totalsPanel = new JPanel(new GridLayout(3, 2));
        totalsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("");
        totalsPanel.add(totalLabel);

        totalsPanel.add(new JLabel("Discounts:"));
        discountLabel = new JLabel("");
        totalsPanel.add(discountLabel);

        totalsPanel.add(new JLabel("Final Total:"));
        finalTotalLabel = new JLabel("");
        totalsPanel.add(finalTotalLabel);

        add(totalsPanel, BorderLayout.SOUTH);

        // Event Listeners and logic for calculating totals and discounts
    }

    // Additional methods for updating cart and totals
}