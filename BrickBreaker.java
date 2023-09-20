import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BrickBreaker extends JFrame implements KeyListener {

    JLabel label;

    public BrickBreaker() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 600);
        this.setLayout(null);
        this.setVisible(true);
        this.addKeyListener(this);
        label = new JLabel();
        label.setBounds(0, 0, 100, 100);
        label.setBackground(Color.red);
        label.setOpaque(true);
        this.add(label);
    }
    // public static void DisplayFrame() {
    // JFrame frame = new JFrame("Brick Breaker");
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // frame.setSize(550, 600);
    // frame.setVisible(true);
    // frame.setLayout(new BorderLayout());
    // JLabel background = new JLabel(new
    // ImageIcon("/Users/mscheie/Documents/Coding/sandbox/bb_background.jpg"));
    // frame.add(background);
    // // background.setLayout(new FlowLayout());

    // brickSlider slider = new brickSlider();
    // slider.paintComponent(null);
    // frame.add(slider);
    // }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("You Pressed Up!");
                label.setLocation(label.getX(), label.getY() - 1);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("You Pressed Down!");
                label.setLocation(label.getX(), label.getY() + 1);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("You Pressed Left!");
                label.setLocation(label.getX() - 1, label.getY());
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("You Pressed Right!");
                label.setLocation(label.getX() + 1, label.getY());
                break;

        }
    }
}