import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BrickBreaker {

    boolean gameOver = false;
    int frameBoundX = 500;
    int frameBoundY = 600;

    public BrickBreaker() {

        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
        JFrame frame = new JFrame("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel panel = new MyPanel();
        panel.setFocusable(true);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

class MyPanel extends JPanel {
    private BufferedImage background;

    Slider slider = new Slider(200, 500, 100, 30);
    ArrayList<Brick> brickList = new ArrayList<Brick>();
    Ball ball = new Ball(slider, brickList);

    Timer gameTimer;
    JLabel welcomeSign;
    boolean isGameRunning = false;

    public MyPanel() {
        setLayout(null);
        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                if ((keycode == KeyEvent.VK_ENTER || keycode == KeyEvent.VK_SPACE) && !isGameRunning) {
                    welcomeSign.setVisible(false);
                    startBall();
                    isGameRunning = true;
                }
                moveSlider(e);
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        // Assigning background image
        try {
            background = ImageIO.read(getClass().getResource("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call to create bricks
        generateBricks();

        // Generate start text
        createWelcomeSign();

        this.gameTimer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check the game condition here and call endGame() when needed
                if (ball.isGameOver()) {
                    endGame();
                }
            }
        });

        gameTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null); // add background
        slider.paintSlider(g); // add slider
        ball.paintBall(g); // add ball
        for (Brick brick : brickList) { // add bricks
            brick.paintBrick(g);
        }
    }

    public void createWelcomeSign() {
        welcomeSign = new JLabel("Press [ENTER] or [SPACE] to start!");
        welcomeSign.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeSign.setForeground(Color.WHITE);
        welcomeSign.setBounds(90, 250, 400, 30);
        add(welcomeSign);
    }

    public void createEndSign(){
        JLabel endSign = new JLabel("GAME OVER");
        endSign.setFont(new Font("Arial", Font.BOLD, 20));
        endSign.setForeground(Color.WHITE);
        endSign.setBounds(90, 250, 400, 30);
        add(endSign);
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

    public void startBall() {
        // The timer is used to repaint the component.
        ball.timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // If ball hits the slider
                if (ball.sliderCollision()) {
                    // Go Up
                    if (ball.x + ball.ballDiameter >= slider.getBounds().x &&
                            ball.x <= slider.getBounds().x + slider.getBounds().width &&
                            ball.y + ball.ballDiameter - 1 <= slider.getBounds().y) {
                        System.out.println("Move up!");
                        ball.move_up = true;
                        // Go Down
                    } else if (ball.x + ball.ballDiameter >= slider.getBounds().x && //
                            ball.x <= slider.getBounds().x + slider.getBounds().width &&
                            ball.y >= slider.getBounds().y + slider.getBounds().height - 1) {
                        System.out.println("Move down!");
                        ball.move_up = false;
                        // Go Left
                    } else if (ball.x <= slider.getBounds().x &&
                            ball.y > slider.getBounds().y - ball.ballDiameter &&
                            ball.y < slider.getBounds().y + slider.getBounds().height) {
                        System.out.println("Go Left");
                        ball.move_left = true;
                        // Go Right
                    } else if (ball.x >= slider.getBounds().x + slider.getBounds().width - 1 &&
                            ball.y > slider.getBounds().y - ball.ballDiameter &&
                            ball.y < slider.getBounds().y + slider.getBounds().height) {
                        System.out.println("Go Right");
                        ball.move_left = false;
                    }
                }

                // If the ball hits a brick
                Brick hitBrick = ball.brickCollision();
                if (hitBrick != null) {
                    // Go Up
                    if (ball.x + ball.ballDiameter >= hitBrick.getBounds().x &&
                            ball.x <= hitBrick.getBounds().x + hitBrick.getBounds().width &&
                            ball.y + ball.ballDiameter - 1 <= hitBrick.getBounds().y) {
                        ball.move_up = true;
                        // Go Down
                    } else if (ball.x + ball.ballDiameter >= hitBrick.getBounds().x && //
                            ball.x <= hitBrick.getBounds().x + hitBrick.getBounds().width &&
                            ball.y >= hitBrick.getBounds().y + hitBrick.getBounds().height - 1) {
                        ball.move_up = false;
                        // Go Left
                    } else if (ball.x <= hitBrick.getBounds().x &&
                            ball.y > hitBrick.getBounds().y - ball.ballDiameter &&
                            ball.y < hitBrick.getBounds().y + hitBrick.getBounds().height) {
                        ball.move_left = true;
                        // Go Right
                    } else if (ball.x >= hitBrick.getBounds().x + hitBrick.getBounds().width - 1 &&
                            ball.y > hitBrick.getBounds().y - ball.ballDiameter &&
                            ball.y < hitBrick.getBounds().y + hitBrick.getBounds().height) {
                        ball.move_left = false;
                    }

                    // Reduce health of brick and remove from screen if no health
                    if (hitBrick.reduceHealth() < 1) {
                        brickList.remove(hitBrick);
                    }
                }

                // If ball hits bottom
                if (ball.y > getHeight() - ball.ballDiameter) {
                    ball.setGameOver(true);
                    return;
                }

                // If the ball hits a wall
                if (ball.y < 0) {
                    ball.move_up = false;
                }

                if (ball.move_up) {
                    ball.y -= 1;
                } else {
                    ball.y += 1;
                }

                // Horizontal
                if (ball.x > 500 - ball.ballDiameter) {
                    ball.move_left = true;
                }

                if (ball.x < 0) {
                    ball.move_left = false;
                }

                if (ball.move_left) {
                    ball.x -= 1;
                } else {
                    ball.x += 1;
                }
                repaint();
            }
        });
        ball.timer.start();
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

    public void endGame() {
        createEndSign();
        this.setEnabled(false);

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

class Brick {

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

    public int reduceHealth() {
        this.health--;
        if (this.health < 1) {
            System.out.println("Brick has zero health! Disappearing!");
            // setVisible(false);
        }
        return this.health;
    }

}

class Ball {

    // Global variables.
    public int x, y; // x, y coordinates of ball
    boolean move_up, move_left; // Direction of ball
    public Slider slider;
    public ArrayList<Brick> brickList;
    public Timer timer;
    private boolean gameOver;
    public final int ballDiameter = 20;

    public Ball(Slider s, ArrayList<Brick> b) {
        this.slider = s;
        this.brickList = b;
        this.x = 220;
        this.y = 480;
        this.move_up = true;
        this.move_left = true;
        this.gameOver = false;
    }

    public void paintBall(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.red);
        g2d.fillOval(x, y, ballDiameter, ballDiameter);
        Toolkit.getDefaultToolkit().sync();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ballDiameter, ballDiameter);
    }

    public boolean sliderCollision() {
        return slider.getBounds().intersects(getBounds());
    }

    public Brick brickCollision() {
        for (Brick brick : brickList) {
            if (brick.getBounds().intersects(getBounds())) {
                return brick;
            }
        }
        return null;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    public void disable() {
        
    }
}