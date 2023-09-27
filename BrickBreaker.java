import java.awt.BorderLayout;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BrickBreaker extends JFrame {

    int frameBoundX = 500;
    int frameBoundY = 600;
    JLabel backgroundLabel;
    Ball ball;
    Slider slider;

    public BrickBreaker() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add new panel
        JPanel panel = new JPanel(new BorderLayout());
        setContentPane(panel);

        // Add slider
        slider = new Slider(this, 200, 500, 100, 30);
        panel.add(slider);
        setVisible(true);

        // Add ball
        ball = new Ball(this, frameBoundX, frameBoundY);
        panel.add(ball);
        setVisible(true);

        Ball ball2 = new Ball(this, frameBoundX, frameBoundY);
        panel.add(ball2);
        setVisible(true);

        Ball ball3 = new Ball(this, frameBoundX, frameBoundY);
        panel.add(ball3);
        setVisible(true);

        Ball ball4 = new Ball(this, frameBoundX, frameBoundY);
        panel.add(ball4);
        setVisible(true);
                
        Ball jfd = new Ball(this, frameBoundX, frameBoundY);
        panel.add(jfd);
        setVisible(true);

        Ball lks = new Ball(this, frameBoundX, frameBoundY);
        panel.add(lks);
        setVisible(true);

        Ball mm = new Ball(this, frameBoundX, frameBoundY);
        panel.add(mm);
        setVisible(true);

        // Add background
        URL backgroundUrl = BrickBreaker.class.getResource("background.jpg");
        ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(),
                backgroundIcon.getIconHeight());
        panel.add(backgroundLabel);
        setVisible(true);

        // Add bricks
    }

}