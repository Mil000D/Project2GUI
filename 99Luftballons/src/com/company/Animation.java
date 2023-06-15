package com.company;

import javax.swing.*;

class Animation extends JFrame {
    BalloonEmergency b = new BalloonEmergency();

    public Animation() {

        Thread t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (b.y + 325 < 0) {
                    dispose();
                    Thread.currentThread().interrupt();
                }
                b.move();
                repaint();
                try {
                    Thread.sleep(1000 / 30);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(b);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        t.start();
    }
}
