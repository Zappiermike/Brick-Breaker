import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

public class BrickBreaker {

    boolean gameOver = false;
    int frameBoundX = 500;
    int frameBoundY = 600;

    public BrickBreaker() {

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
    JLabel welcomeSign, endRoundSign, endGameSign, victorySign, scoreLabel, playAgain, scoreboard;
    int score;
    int round = 1;
    int MAXROUNDS = 4;
    boolean roundWon = false;
    boolean isGameRunning = false;

    public MyPanel() {
        score = 0;
        setLayout(null);
        generateSigns();
        loadBall();
        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                if ((keycode == KeyEvent.VK_ENTER || keycode == KeyEvent.VK_SPACE) && !isGameRunning) {
                    welcomeSign.setVisible(false);
                    releaseBall();
                    isGameRunning = true;
                } else if (keycode == KeyEvent.VK_ENTER && isGameRunning && 
                           roundWon){
                    ++round;
                    System.out.println("Round is now " + round);
                    resetComponents();
                    roundWon = false;
                    isGameRunning = false;
                }
                moveSlider(e);
            }

            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        // Assigning background image
        try {
            background = ImageIO.read(getClass().getResource("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayWelcomeSign();
        displayScoreboard();
        generateBricks(round);

        gameTimer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check the game condition here and call endGame() when needed
                if (ball.isGameOver() && !brickList.isEmpty()) {
                    endGame();
                }
                if (!ball.isGameOver() && brickList.isEmpty()){
                    if (round >= MAXROUNDS){
                        winGame();
                    } else {
                        endRound();
                    }
                }
            }
        });

        gameTimer.start();
    }

    public void generateSigns(){
        String welcomeMsg = "Press [ENTER] or [SPACE] to start!";
        welcomeSign = new JLabel(welcomeMsg);
        welcomeSign.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeSign.setForeground(Color.WHITE);
        welcomeSign.setBounds(90, 250, 400, 30);
        welcomeSign.setVisible(false);
        
        String roundWonMsg = "ROUND WON!";
        endRoundSign = new JLabel(roundWonMsg);
        endRoundSign.setFont(new Font("Arial", Font.BOLD, 20));
        endRoundSign.setForeground(Color.WHITE);
        endRoundSign.setBounds(170, 250, 500, 30);
        endRoundSign.setVisible(false);

        String gameOverMsg = "GAME OVER";
        endGameSign = new JLabel(gameOverMsg);
        endGameSign.setFont(new Font("Arial", Font.BOLD, 20));
        endGameSign.setForeground(Color.WHITE);
        endGameSign.setBounds(187, 250, 500, 30);
        endGameSign.setVisible(false);

        String victoryMsg = "YOU WIN!";
        victorySign = new JLabel(victoryMsg);
        victorySign.setFont(new Font("Arial", Font.BOLD, 30));
        victorySign.setForeground(Color.WHITE);
        victorySign.setBounds(180, 250, 500, 100);
        victorySign.setVisible(false);

        scoreboard = new JLabel("Score:");
        scoreboard.setFont(new Font("Arial", Font.BOLD, 20));
        scoreboard.setForeground(Color.WHITE);
        scoreboard.setBounds(350, 570, 70, 30);
        scoreboard.setVisible(false);

        scoreLabel = new JLabel();
        scoreLabel.setText(String.valueOf(score));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(420, 570, 70, 30);
        scoreLabel.setVisible(false);
        
        String continueMsg = String.format("Press [ENTER] to continue " +
                                           "to Round 2");
        playAgain = new JLabel(continueMsg);
        playAgain.setFont(new Font("Arial", Font.BOLD, 20));
        playAgain.setForeground(Color.WHITE);
        playAgain.setBounds(60, 300, 400, 30);
        playAgain.setVisible(false);
        
        add(welcomeSign);
        add(endRoundSign);
        add(victorySign);
        add(endGameSign);
        add(scoreboard);
        add(scoreLabel);
        add(playAgain);
    }

    public void resetComponents(){
        ball.timer.stop();
        ball.x = 220;
        ball.y = 480;
        ball.setSpeedX(1.0f);
        ball.setSpeedY(1.0f);
        generateBricks(round);
        slider.setX(200);
        playAgain.setVisible(false);
        endRoundSign.setVisible(false);
        repaint();
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

    public void displayWelcomeSign() {
        welcomeSign.setVisible(true);
    }

    public void displayEndRound(){
        endRoundSign.setVisible(true);
        playAgain.setText(String.format("Press [ENTER] to continue to Round %d", round+1));
        playAgain.setVisible(true);
    }

    public void displayEndSign() {
        endGameSign.setVisible(true);
    }

    public void displayVictorySign() {
        victorySign.setVisible(true);
    }

    public void displayScoreboard() {
        scoreLabel.setVisible(true);
        scoreboard.setVisible(true);
    }

    private void increaseScore(int amount) {
        score += amount;
        scoreLabel.setText(String.valueOf(score));
    }

    public void generateBricks(int round) {
        try {
            File file = new File("./Brick-Breaker/brickConfig.json");
            if (file.exists()) {
                Object obj = new JSONParser().parse(new FileReader(file));
                JSONObject brickConfig = (JSONObject) obj;
                JSONObject roundKey = (JSONObject) brickConfig.get(Integer.toString(round));

                int startRow    = ((Long) roundKey.get("startRow")).intValue();
                int endRow      = ((Long) roundKey.get("endRow")).intValue();
                int incRow      = ((Long) roundKey.get("incRow")).intValue();
                int startCol    = ((Long) roundKey.get("startCol")).intValue();
                int endCol      = ((Long) roundKey.get("endCol")).intValue();
                int incCol      = ((Long) roundKey.get("incCol")).intValue();
                int brickWidth  = ((Long) roundKey.get("brickWidth")).intValue();
                int brickHeight = ((Long) roundKey.get("brickHeight")).intValue();
                int health      = ((Long) roundKey.get("health")).intValue();
                int incHealth   = ((Long) roundKey.get("incHealth")).intValue();
                String shape    = (String) roundKey.get("shape");
                
                System.out.println("Loading Round " + round);
                switch (shape) {
                    case "rectangle":
                        for (int r = startRow; r < endRow; r += incRow){
                            for (int c = startCol; c < endCol; c += incCol){
                                Brick brick = new Brick(c, r, health, brickWidth, brickHeight);
                                brickList.add(brick);
                            }
                            if (health > 1){
                                health -= incHealth;
                            }
                        }
                        break;
                    case "invertedTriangle":
                        for (int r = startRow; r < endRow; r += incRow){
                            int offset = (r / incRow) * (incCol);
                            for (int c = startCol + offset; c < endCol - offset; c += incCol){
                                Brick brick = new Brick(c, r, health, brickWidth, brickHeight);
                                brickList.add(brick);
                            }
                            if (health > 1){
                                health -= incHealth;
                            }
                        }
                        break;
                    case "triangle":
                        for (int r = startRow; r < endRow; r += incRow){
                            int offset = Math.abs((r / incRow) - (endRow/incRow)) * (incCol);
                            for (int c = startCol + offset; c < endCol - offset; c += incCol){
                                System.out.println(c);
                                Brick brick = new Brick(c, r, health, brickWidth, brickHeight);
                                brickList.add(brick);
                            }
                            if (health > 1){
                                health -= incHealth;
                            }
                        }
                        break;
                    case "dev":
                        Brick brick = new Brick(100, 200, 1, 100, 30);
                        brickList.add(brick);
                        break;
                    default:
                        System.out.println("Error - No shape found for round!"); 
                        break;  
                }
            } else {
                System.out.println("File does not exist: " + file.getAbsolutePath());
            }
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    public void calcNewBallDirection() {
        double ballCenterX = ball.x + ball.ballDiameter / 2;
        double paddleWidth = slider.getWidth();
        double paddleCenterX = slider.getX() + paddleWidth / 2;
        double speedX = ball.speedX;
        double speedY = ball.speedY;

        // Calculate Hypotenuse 
        double speedXY = Math.sqrt(speedX * speedX + speedY * speedY);

        // Calculate the position of the ball relative to the center of the
        // slider. Value will be between -1 and 1.
        double posX = (ballCenterX - paddleCenterX) / (paddleWidth / 2);

        // Influence on strong the angle reflection can be 
        final double influenceX = 0.75;

        // Calculate new speed for X
        speedX = speedXY * posX * influenceX;

        // Calculate speedY based on Hypotenuse and speedX
        speedY = Math.sqrt(speedXY * speedXY - speedX * speedX) * (speedY > 0 ? -1 : 1);

        ball.setSpeedX(speedX);
        ball.setSpeedY(speedY);
    }

    public void loadBall() {
        // The timer is used to repaint the component.
        ball.timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // If ball hits the slider
                if (ball.sliderCollision()) {
                    increaseScore(10);
                    // Go Up
                    if (ball.x + ball.ballDiameter >= slider.getBounds().x &&
                            ball.x <= slider.getBounds().x + slider.getBounds().width &&
                            ball.y + ball.ballDiameter - 1 <= slider.getBounds().y) {
                        calcNewBallDirection();
                    }
                    // Go Left
                    else if (ball.x <= slider.getBounds().x &&
                            ball.y > slider.getBounds().y - ball.ballDiameter &&
                            ball.y < slider.getBounds().y + slider.getBounds().height) {
                        ball.speedX *= -1;
                    }
                    // Go Right
                    else if (ball.x >= slider.getBounds().x + slider.getBounds().width - 1 &&
                            ball.y > slider.getBounds().y - ball.ballDiameter &&
                            ball.y < slider.getBounds().y + slider.getBounds().height) {
                        ball.speedX *= -1;
                    }
                }

                // If the ball hits a brick
                Brick hitBrick = ball.brickCollision();
                if (hitBrick != null) {
                    increaseScore(100);
                    int ballX = (int) ball.x;
                    int ballY = (int) ball.y;
                    int ballD = ball.ballDiameter;
                    int brickX = hitBrick.getBounds().x;
                    int brickY = hitBrick.getBounds().y;
                    int brickH = hitBrick.getBounds().height;
                    int brickW = hitBrick.getBounds().width;

                    // Go Up
                    if (ballX + ballD >= brickX && ballX <= brickX + brickW &&
                            ballY + ballD - 3 <= brickY) {
                        ball.speedY *= -1;
                    }

                    // Go Down
                    else if (ballX + ballD >= brickX && //
                            ballX <= brickX + brickW &&
                            ballY >= brickY + brickH - 3) {
                        ball.speedY *= -1;
                    }

                    // Go Left
                    else if (ballX <= brickX &&
                            ballY > brickY - ballD &&
                            ballY < brickY + brickH) {
                        ball.speedX *= -1;
                    }

                    // Go Right
                    else if (ballX >= brickX + brickW - 1 &&
                            ballY > brickY - ballD &&
                            ballY < brickY + brickH) {
                        ball.speedX *= -1;
                    }

                    // Reduce health of brick and remove from screen if no health
                    if (hitBrick.reduceHealth() < 1) {
                        brickList.remove(hitBrick);
                    }
                }

                // Ball hits sidewalls
                if (ball.x > 500 - ball.ballDiameter || ball.x < 0) {
                    ball.speedX *= -1;
                }

                // Ball hits ceiling
                if (ball.y < 0) {
                    ball.speedY *= -1;
                }

                // Ball hits floor
                if (ball.y > getHeight() - ball.ballDiameter) {
                    if(!roundWon){
                        ball.setGameOver(true);
                    }
                    return;
                }

                ball.y += ball.speedY;
                ball.x += ball.speedX;
                repaint();
            }
        });
    }

    public void releaseBall(){
        ball.timer.start();
    }

    public void moveSlider(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int speed = 20;
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (slider.getX() - speed >= 0) {
                    slider.setX(slider.getX() - speed);
                    repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
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

    public void endRound(){
        roundWon = true;
        displayEndRound();
    }

    public void endGame() {
        displayEndSign();
        this.setEnabled(false);
    }
    
    public void winGame(){
        displayVictorySign();
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
    private int health;
    // private final int startingHealth;
    private int width;
    private int height;

    HashMap<Integer, Color> healthColor = new HashMap<Integer, Color>(){{
        put(1, Color.MAGENTA);
        put(2, Color.CYAN);
        put(3, Color.YELLOW);
    }};

    public Brick(int startingX, int startingY, int health, int width, int height) {
        this.x = startingX;
        this.y = startingY;
        this.health = health;
        this.width = width;
        this.height = height;
        // this.startingHealth = health;
    }

    public void paintBrick(Graphics g) {
        // g.setColor(healthColor.get(this.startingHealth));  // No color change
        g.setColor(healthColor.get(this.health));
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int reduceHealth() {
        this.health--;
        return this.health;
    }
}

class Ball {

    // Global variables.
    public int centerX;
    public Slider slider;
    public ArrayList<Brick> brickList;
    public Timer timer;
    public double x, y;
    public double speedX, speedY;
    private boolean gameOver;
    public final int ballDiameter = 20;
    public final int ballRadius = ballDiameter / 2;

    public Ball(Slider s, ArrayList<Brick> b) {
        this.slider = s;
        this.brickList = b;
        this.x = 220;
        this.y = 480;
        this.gameOver = false;
        this.speedX = 1;
        this.speedY = 1;
    }

    public void paintBall(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.red);
        g2d.fillOval((int) x, (int) y, ballDiameter, ballDiameter);
        Toolkit.getDefaultToolkit().sync();
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, ballDiameter, ballDiameter);
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

    public void setSpeedX(double newSpeedX) {
        this.speedX = newSpeedX;
    }

    public void setSpeedY(double newSpeedY) {
        this.speedY = newSpeedY;
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }
}