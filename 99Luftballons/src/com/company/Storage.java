package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Storage extends Thread {
    public List<Balloon> list;
    public JLabel labelStorage = new JLabel();

    public Storage() {
        this.list = new ArrayList<>();
        start();
    }

    public void addToStorage(Balloon balloon) {
        list.add(balloon);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (list.size() >= 90) {
                while (list.size() > 0) {
                    list.remove(0);
                }
                new Animation();
            }
            labelStorage.setFont(new Font("Serif", Font.PLAIN, 50));
            labelStorage.setText("Storage stores : " + list.size() + " balloons");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

}
