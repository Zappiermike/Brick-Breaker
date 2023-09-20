import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BrickBreaker extends JFrame implements KeyListener {

    JLabel sliderLabel;
    JLabel background;
    ImageIcon icon;

    public BrickBreaker() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 600);
        this.setLayout(null);
        this.setVisible(true);
        this.addKeyListener(this);

        sliderLabel = new JLabel();
        sliderLabel.setBounds(200, 500, 100, 100);
        sliderLabel.setOpaque(true);
        this.add(sliderLabel);

        URL url = BrickBreaker.class.getResource("blueslider.png");
        icon = new ImageIcon(url);
        sliderLabel.setIcon(icon);

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
        int keyCode = e.getKeyCode();
        int speed = 20;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("You Pressed Up!");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("You Pressed Down!");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("You Pressed Left!");
                if (sliderLabel.getX() - speed >= 0) {
                    sliderLabel.setLocation(sliderLabel.getX() - speed, sliderLabel.getY());
                }
                System.out.println("labelx: " + sliderLabel.getX() + " thisx: " + this.getX());
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("You Pressed Right!");
                if (sliderLabel.getX() + 100 < this.getSize().getWidth()) {
                    sliderLabel.setLocation(sliderLabel.getX() + speed, sliderLabel.getY());
                }
                System.out.println("labelx: " + sliderLabel.getX() + " thisx: " + this.getSize().getWidth());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println();
    }
}