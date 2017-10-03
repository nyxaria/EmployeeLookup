package com.nyxaria.apps.View;

import com.nyxaria.apps.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.HashMap;

public class DataPane extends JPanel {

    Main main;
    public JTable table;

    public DataPane(Main main) {
        super(new BorderLayout());
        populate(main.dataHandler.getColumns(), main.dataHandler.processHashmap(main.dbHandler.getData()));
        revalidate();
        this.main = main;
    }


    public void populate(String[] columns, String[][] rows) {
        removeAll();
        revalidate();
        repaint();

        for (int i = 0; i < columns.length; i++) { //capitalize
            columns[i] = columns[i].substring(0, 1).toUpperCase() + columns[i].substring(1);
        }


        table = new JTable(rows, columns);
        table.setBorder(null);
        JScrollPane tableScroll = new JScrollPane(table);

        resizeColumnWidth(table);

        table.setDefaultEditor(Object.class, null);

        add(tableScroll);

    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
