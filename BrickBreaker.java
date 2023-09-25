import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.layout.Border;

public class BrickBreaker extends JFrame {

    int frameBoundX = 500;
    int frameBoundY = 600;
    // JLabel sliderLabel;
    JLabel backgroundLabel;
    Ball ball;

    public BrickBreaker() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Add new panel
        JPanel panel = new JPanel(new BorderLayout());
        setContentPane(panel);
        
        // Add slider
        Slider slider = new Slider(200, 500, 100, 30);
        panel.add(slider);
        setVisible(true);
        
        // Add ball
        ball = new Ball(frameBoundX, frameBoundY);
        panel.add(ball);
        setVisible(true);

        // Add background
        URL backgroundUrl = BrickBreaker.class.getResource("background.jpg");
        ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(),
        backgroundIcon.getIconHeight());
        panel.add(backgroundLabel);
        setVisible(true);
        
        
    }

}