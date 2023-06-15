package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Random;

class Factory {
    private long counter;
    private static long nextNumber = 1;
    private long number;
    private final JPanel panel = new JPanel();
    private final JLabel label1 = new JLabel();
    private int startingPosition = 1500;


    public Factory(Storage storage) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Balloon balloon = new Balloon(BalloonColor.values()[new Random().nextInt(BalloonColor.values().length)]);
                counter++;
                if (storage.list.size() < 99) {
                    storage.addToStorage(balloon);
                }
                label1.setText("Factory " + number + " produced " + counter + " balloons");
                try {
                    Thread.sleep(startingPosition);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        this.number = nextNumber;
        nextNumber++;
        panel.setLayout(new GridLayout(1, 3));
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.add(label1);
        Action delete =
                new AbstractAction("Delete") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panel.setVisible(false);
                        thread.interrupt();
                    }
                };
        JSlider slider = new JSlider(JSlider.HORIZONTAL,
                100, 3000, 1500);
        slider.setMajorTickSpacing(100);
        slider.setPaintTicks(true);
        slider.addChangeListener(event -> {
            if (!slider.getValueIsAdjusting()) {
                startingPosition = slider.getValue();
            }
        });
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(100, new JLabel("100ms"));
        labelTable.put(1500, new JLabel("1500ms"));
        labelTable.put(3000, new JLabel("3000ms"));
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
        panel.add(slider);
        panel.add(new JButton(delete));
        thread.start();
    }

    public JPanel getPanel() {
        return panel;
    }

}
