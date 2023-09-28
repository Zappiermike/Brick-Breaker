import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BrickBreaker {

    boolean gameOver = false;
    int frameBoundX = 500;
    int frameBoundY = 600;
    JFrame frame;
    JPanel panel;
    Ball ball;
    Slider slider;
    ArrayList<Brick> brickList = new ArrayList<Brick>();

    public BrickBreaker() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameBoundX, frameBoundY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Add new panel
        panel = new JPanel(new BorderLayout());
        frame.setContentPane(panel);

        // Add slider
        slider = new Slider(200, frameBoundY - 100, 100, 30);
        panel.add(slider);
        frame.setVisible(true);

        // Add ball
        ball = new Ball(this, frameBoundX, frameBoundY);
        panel.add(ball);
        frame.setVisible(true);

        // Add Bricks
        generateBricks();
        for (Brick brick : brickList) {
            panel.add(brick);
            frame.setVisible(true);
        }

        // Add background
        URL backgroundUrl = BrickBreaker.class.getResource("background.jpg");
        ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(),
                backgroundIcon.getIconHeight());
        panel.add(backgroundLabel);
        frame.setVisible(true);
    }

    public void startGame() {
        // Add ball
        // ball = new Ball(this, frameBoundX, frameBoundY);
        // panel.add(ball);
        // setVisible(true);
        // panel.repaint();
        // panel.revalidate();

        slider.start();
        ball.start();
    }

    public void endGame() {
        System.out.println("GAME OVER");
        // JLabel endSign = new JLabel("MY TEXT HERE");
        // endSign.setText("GAME OVER");
        // endSign.setBounds(frameBoundX/2, frameBoundY/2, 200, 200);
        // endSign.setForeground(Color.white);
        // endSign.setVisible(true);
        // panel.add(endSign);
        // panel.setVisible(true);
        // // Repaint the panel
        // panel.revalidate();
        // panel.repaint();
        slider.end();
    }

    public boolean isGameOver() {
        return gameOver;
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