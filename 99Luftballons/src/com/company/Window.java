package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

class Window extends JFrame {
    Storage storage = new Storage();

    public Window() {
        super("99 Luftballons");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel factoryPanel1 = new JPanel();
        factoryPanel1.setBorder(new BevelBorder(BevelBorder.RAISED));
        factoryPanel1.setLayout(new BoxLayout(factoryPanel1, BoxLayout.PAGE_AXIS));
        Action addFactory =
                new AbstractAction("ADD FACTORY") {
                    public void actionPerformed(ActionEvent e) {
                        Factory factory = new Factory(storage);
                        factoryPanel1.add(factory.getPanel());
                    }
                };
        JPanel panelFactory2 = new JPanel();
        panelFactory2.add(new JButton(addFactory));
        panelFactory2.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelFactory2.setPreferredSize(new Dimension(1000, 40));
        factoryPanel1.add(panelFactory2);
        add(factoryPanel1, BorderLayout.NORTH);

        JPanel storagePanel = new JPanel();
        storagePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        storagePanel.setPreferredSize(new Dimension(1000, 200));
        storagePanel.add(storage.labelStorage, BorderLayout.CENTER);
        add(storagePanel, BorderLayout.CENTER);

        JPanel transporter = new JPanel();
        transporter.setBorder(new BevelBorder(BevelBorder.RAISED));
        transporter.setLayout(new BoxLayout(transporter, BoxLayout.PAGE_AXIS));
        Action addTransporter =
                new AbstractAction("ADD TRANSPORTER") {
                    public void actionPerformed(ActionEvent e) {
                        Transporter transporter1 = new Transporter(storage);
                        transporter.add(transporter1.getPanel());
                    }
                };

        JPanel panelTransporter = new JPanel();
        panelTransporter.add(new JButton(addTransporter));
        panelTransporter.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelTransporter.setPreferredSize(new Dimension(1000, 40));
        transporter.setBorder(new BevelBorder(BevelBorder.RAISED));
        transporter.add(panelTransporter);
        add(transporter, BorderLayout.SOUTH);

        JScrollPane scrollFrame1 = new JScrollPane(factoryPanel1);
        scrollFrame1.setPreferredSize(new Dimension(1000, 200));
        add(scrollFrame1, BorderLayout.NORTH);

        JScrollPane scrollFrame2 = new JScrollPane(transporter);
        scrollFrame2.setPreferredSize(new Dimension(1000, 200));
        add(scrollFrame2, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
