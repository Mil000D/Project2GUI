import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}

enum BalloonColor {
    Red,
    Pink,
    Orange,
    Yellow,
    Purple,
    Green,
    Blue,
    Brown,
    White,
    Black,
    Gray,
    Gold,
    Cyan,
    Crimson,
    Magenta,
    Olive
}

class Balloon {
    private BalloonColor color;
    private static long nextNumber = 1;


    public Balloon(BalloonColor color) {
        nextNumber++;
        this.color = color;
    }

}

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

class Animation extends JFrame {
    BalloonEmergency b = new BalloonEmergency();

    public Animation() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
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





