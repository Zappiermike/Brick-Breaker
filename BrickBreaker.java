import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javafx.scene.image.Image;

public class BrickBreaker{
    public static void DisplayFrame() throws IOException{
        JFrame frame = new JFrame("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JLabel emptyLabel = new JLabel(new ImageIcon(ImageIO.read(new File("bb_background.jpg"))));
        JLabel emptyLabel = new JLabel("");

        emptyLabel.setPreferredSize(new Dimension(550, 600));

        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        // Add background image
        frame.setContentPane(emptyLabel);

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    DisplayFrame();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}

