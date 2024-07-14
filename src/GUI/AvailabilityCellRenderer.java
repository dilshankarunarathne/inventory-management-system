package GUI;

import core.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class AvailabilityCellRenderer extends DefaultTableCellRenderer {
    private final List<Product> products;

    public AvailabilityCellRenderer(List<Product> products) {
        this.products = products;
    }

    private Product findProductById(String productId) {
        for (Product product : this.products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String productId = (String) table.getValueAt(row, 0);
        Product product = findProductById(productId);
        if (product != null && product.getAvailableItems() < 3) {
            c.setForeground(Color.WHITE);
            c.setBackground(Color.RED);
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        return c;
    }
}
