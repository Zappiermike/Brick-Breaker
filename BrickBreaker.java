import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BrickBreaker {

    private Timer gameTime;
    boolean gameOver = false;
    int frameBoundX = 500;
    int frameBoundY = 600;
    JFrame frame;
    JPanel panel;
    Ball ball;
    notASlider slider;
    ArrayList<notABrick> brickList = new ArrayList<notABrick>();

    public BrickBreaker() {

        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Brick Breaker");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MyPanel();
        panel.setFocusable(true);
        f.add(panel);
        f.pack();
        f.setVisible(true);

        // frame = new JFrame();
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(frameBoundX, frameBoundY);
        // frame.setLocationRelativeTo(null);
        // frame.setResizable(false);

        // // Add new panel
        // panel = new JPanel(new BorderLayout());
        // frame.setContentPane(panel);

        // // Add ball
        // // ball = new Ball(this, frameBoundX, frameBoundY);
        // // panel.add(ball);
        // // frame.setVisible(true);

        // // Add Bricks
        // generateBricks();
        // for (Brick brick : brickList) {
        // panel.add(brick);
        // frame.setVisible(true);
        // }

        // // Add background
        // URL backgroundUrl = BrickBreaker.class.getResource("background.jpg");
        // ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        // JLabel backgroundLabel = new JLabel(backgroundIcon);
        // backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(),
        // backgroundIcon.getIconHeight());
        // panel.add(backgroundLabel);
        // frame.setVisible(true);

        gameTime = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check the game condition here and call endGame() when needed
                if (isGameOver()) {
                    endGame();
                }
            }
        });

        // Start the Timer
        gameTime.start();
    }

    public void startGame() {
        // Add ball
        // ball = new Ball(this, frameBoundX, frameBoundY);
        // panel.add(ball);
        // setVisible(true);
        // panel.repaint();
        // panel.revalidate();

        // slider.start();
        // ball.start();
    }

    public void endGame() {
        // System.out.println("GAME OVER");
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
        // slider.end();
        // gameTime.stop();
    }

    public boolean isGameOver() {
        return gameOver;
    }



}

class MyPanel extends JPanel {
    ArrayList<Brick> brickList = new ArrayList<Brick>();

    // Add slider
    Slider slider = new Slider(200, 500, 100, 30);

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));

        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed!");
                moveSlider(e);
            }

            public void keyReleased(KeyEvent e) {
                System.out.println();
            }

            public void keyTyped(KeyEvent e) {
                System.out.println();
            }
        });

        generateBricks();
    }

    public void generateBricks() {
        // Unique starting and iterating numbers are due to the window size
        // while also spacing out the bricks evenly
        for (int row = 10; row <= 85; row += 35) {
            for (int b = 5; b < 500; b += 99) {
                System.out.println("Generating brick #" + b);
                Brick brick = new Brick(b, row);
                brickList.add(brick);
            }
        }
    }

    public void moveSlider(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int speed = 20;
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                System.out.println("Go Left!");
                if (slider.getX() - speed >= 0) {
                    slider.setX(slider.getX() - speed);
                    System.out.println(slider.getX());
                    repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Go Right!");
                if (slider.getX() + speed <= 500 - slider.getWidth()) {
                    slider.setX(slider.getX() + speed);
                    repaint();
                }
                break;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 600);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        slider.paintSlider(g);
        for (Brick brick : brickList) {
            brick.paintBrick(g);
        }

    }
}

class Slider {

    private int x;
    private final int y;
    private int width;
    private int height;

    public Slider(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void paintSlider(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

class Brick{

    private int x;
    private int y;
    private int width = 94;
    private int height = 30;
    private int health = 1;

    public Brick(int startingX, int startingY) {
        this.x = startingX;
        this.y = startingY;
    }

    public void paintBrick(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int reduceHealth(){
        this.health--;
        if (this.health < 1){
            System.out.println("Brick has zero health! Disappearing!");
            // setVisible(false);
        }
        return this.health;
    }

}