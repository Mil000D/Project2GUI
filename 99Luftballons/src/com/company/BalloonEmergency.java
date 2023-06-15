package com.company;

import javax.swing.*;
import java.awt.*;

class BalloonEmergency extends JComponent {
    public int x, y;
    public int vy;

    public BalloonEmergency() {
        this.x = 100;
        this.y = 500;
        vy = 6;
    }

    public void move() {
        y -= vy;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Serif", Font.PLAIN, 75));
        g.setColor(Color.red);
        g.drawString("EMERGENCY", 100, 100);
        g.drawString("RELEASE !!!", 100, 200);
        g.setColor(Color.yellow);
        g.fillOval(x, y, 100, 125);
        g.setColor(Color.blue);
        g.fillOval(x + 100, y + 100, 100, 125);
        g.setColor(Color.green);
        g.fillOval(x + 200, y, 100, 125);
        g.setColor(Color.magenta);
        g.fillOval(x + 300, y + 100, 100, 125);
        g.setColor(Color.orange);
        g.fillOval(x + 400, y, 100, 125);
        g.setColor(Color.black);
        int xx = x + 50;
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                g.drawRect(xx + i * 100, y + 125, 1, 100);
            } else {
                g.drawRect(xx + i * 100, y + 225, 1, 100);
            }
        }
    }
}
