package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

class Transporter {
    private List<Balloon> batch;
    private static long nextNumber = 1;
    private long number;
    private final JPanel panel = new JPanel();
    private final JLabel label = new JLabel();
    private boolean suspend = false;

    public Transporter(Storage storage) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (suspend) {
                    label.setText("Transporter " + number + " stopped");
                } else {
                    synchronized (storage.list) {
                        if (!suspend && storage.list.size() >= 10) {
                            for (int i = 0; i < 10; i++) {
                                batch.add(storage.list.get(0));
                                storage.list.remove(0);
                            }
                        }
                    }
                    if (batch.size() != 10) {
                        label.setText("Transporter " + number + " waiting");
                    } else {
                        label.setText("Transporter " + number + " loading");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        label.setText("Transporter " + number + " delivering");
                        while (batch.size() > 0) {
                            batch.remove(0);
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });
        this.number = nextNumber;
        nextNumber++;
        this.batch = new ArrayList<>();

        panel.setLayout(new GridLayout(1, 3));
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(label);
        Action stop =
                new AbstractAction("Stop") {
                    public void actionPerformed(ActionEvent e) {
                        suspend = true;
                    }
                };
        panel.add(new JButton(stop));
        Action start =
                new AbstractAction("Start") {
                    public void actionPerformed(ActionEvent e) {
                        suspend = false;
                    }
                };
        panel.add(new JButton(start));
        thread.start();
    }

    public JPanel getPanel() {
        return panel;
    }
}
