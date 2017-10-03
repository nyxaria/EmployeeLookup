package com.nyxaria.apps.View;

import com.nyxaria.apps.Main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DataFrame extends JFrame {

    private Main main;
    Container mainPane;
    public DataPane dataPane;

    public HashMap<String, JTextField> fields;

    public DataFrame(Main main) {
        super();
        this.main = main;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();

        setVisible(true);
        dataPane.table.requestFocus();

    }

    private void initUI() {
        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());

        JPanel filtersPane = new JPanel();
        filtersPane.setLayout(new BoxLayout(filtersPane, BoxLayout.Y_AXIS));

        fields = new HashMap<String, JTextField>();
        for (String s : main.dataHandler.getColumns()) { //create labels + fields
            if (s.equals("age") || s.equals("gender")) continue;
            JPanel contentWrap = new JPanel(new FlowLayout(FlowLayout.LEFT));
            contentWrap.setPreferredSize(new Dimension(300, 30));
            contentWrap.setMaximumSize(contentWrap.getPreferredSize());

            JLabel contentLabel = new JLabel(s.substring(0, 1).toUpperCase() + s.substring(1) + ":", SwingConstants.RIGHT); //capitalize
            contentLabel.setPreferredSize(new Dimension(80, 20));

            JTextField contentField = new JTextField(12);
            contentField.getDocument().putProperty("tag", s);
            contentField.getDocument().addDocumentListener(fieldListener);
            fields.put(s, contentField);

            contentWrap.add(contentLabel, BorderLayout.WEST);
            contentWrap.add(contentField, BorderLayout.EAST);

            filtersPane.add(contentWrap);
        }

        JPanel filtersWrap = new JPanel(new BorderLayout());

        filtersWrap.add(filtersPane, BorderLayout.CENTER);

        JButton exportToExcel = new JButton("Export to Excel");
        exportToExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                java.awt.FileDialog dialog = new java.awt.FileDialog((java.awt.Frame) null);
                System.setProperty("apple.awt.fileDialogForDirectories", "true");

                dialog.setVisible(true);
                String name = JOptionPane.showInputDialog("Name of Excel file: ");
                if (name.equals("") || dialog.getDirectory().equals("")) return;
                main.dataHandler.export(dataPane.table, dialog.getDirectory(), name);
            }
        });

        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrap.add(exportToExcel);

        filtersWrap.add(wrap, BorderLayout.SOUTH);

        mainPane.add(filtersWrap, BorderLayout.WEST);

        dataPane = new DataPane(main);

        mainPane.add(dataPane, BorderLayout.EAST);
        setContentPane(mainPane);

    }

    DocumentListener fieldListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
            main.dataHandler.updateUI(fields);
        }

        public void removeUpdate(DocumentEvent e) {
            main.dataHandler.updateUI(fields);
        }

        public void insertUpdate(DocumentEvent e) {
            main.dataHandler.updateUI(fields);
        }
    };
}
