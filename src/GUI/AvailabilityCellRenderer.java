package GUI;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.Color;

public class AvailabilityCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 4) { // Assuming the 'Items Available' column is at index 4
            int availableItems = Integer.parseInt(value.toString());
            if (availableItems < 3) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
        }
        return c;
    }
}
