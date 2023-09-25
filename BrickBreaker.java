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

        // JPanel panel = new JPanel();
        // panel.setLayout(null);
        // setContentPane(panel);

        // URL backgroundUrl = BrickBreaker.class.getResource("bb_background.jpg");
        // ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        // backgroundLabel = new JLabel(backgroundIcon);
        // backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(),
        // backgroundIcon.getIconHeight());

        // Slider slider = new Slider();
        // panel.add(slider);
        // panel.add(backgroundLabel);

        // this.add(new Slider());

        // URL sliderUrl = BrickBreaker.class.getResource("blueslider.png");
        // ImageIcon sliderIcon = new ImageIcon(sliderUrl);
        // sliderLabel = new JLabel();
        // sliderLabel.setBounds(200, 500, 100, 30);
        // sliderLabel.setOpaque(true);
        // sliderLabel.setIcon(sliderIcon);

        // this.add(backgroundLabel);
        // this.add(sliderLabel);
        // this.add(new Slider());
        // this.add(new Ball(frameBoundX, frameBoundY));
        // this.setVisible(true);

    }

}