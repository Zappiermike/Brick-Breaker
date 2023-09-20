import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BrickBreaker {
    public static void DisplayFrame() {
        JFrame frame = new JFrame("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(550, 600);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("/Users/mscheie/Documents/Coding/sandbox/bb_background.jpg"));
        frame.add(background);
        background.setLayout(new FlowLayout());

        brickSlider slider = new brickSlider();
        frame.add(slider);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    DisplayFrame();
                }
            });
        }
    }
