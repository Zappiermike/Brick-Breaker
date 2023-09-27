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
    ArrayList<Brick> brickList = new ArrayList<Brick>();

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

        generateBricks();
        for (Brick brick : brickList) {
            panel.add(brick);
            setVisible(true);
            ;
        }
        // Brick brick = new Brick(200, 250);
        // panel.add(brick);
        // setVisible(true);

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

    public void generateBricks() {
        // Unique starting and iterating numbers are due to the window size
        // while also spacing out the bricks evenly
        for (int row = 10; row <= 85; row += 35) {
            for (int b = 5; b < frameBoundX; b += 99) {
                System.out.println("Generating brick #" + b);
                Brick brick = new Brick(b, row);
                brickList.add(brick);
            }
        }
    }

}