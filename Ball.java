import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Rectangle;

public class Ball extends JComponent {

    // Global variables.
    private int x = 220; // Starting x coordinate
    private int y = 480; // Starting y coordinate
    private final int ballDiameter = 20;
    private int frameBoundX, frameBoundY;
    boolean move_up = true;
    boolean move_left = true;
    private BrickBreaker game;
    private Timer timer;

    public Ball(BrickBreaker game, int xb, int yb) {
        this.frameBoundX = xb;
        this.frameBoundY = yb;
        this.game = game;

        // The timer is used to repaint the component.
        timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // If ball hits the slider
                if (sliderCollision()) {
                    if (x + ballDiameter >= game.slider.getBounds().x &&
                            x <= game.slider.getBounds().x + game.slider.getBounds().width &&
                            y + ballDiameter - 1 <= game.slider.getBounds().y) {
                        System.out.println("Move up!");
                        move_up = true;
                    } else if (x + ballDiameter >= game.slider.getBounds().x && //
                            x <= game.slider.getBounds().x + game.slider.getBounds().width &&
                            y >= game.slider.getBounds().y + game.slider.getBounds().height - 1) {
                        System.out.println("Move down!");
                        move_up = false;
                    } else if (x <= game.slider.getBounds().x &&
                            y > game.slider.getBounds().y - ballDiameter &&
                            y < game.slider.getBounds().y + game.slider.getBounds().height) {
                        System.out.println("Go Left");
                        move_left = true;
                    } else if (x >= game.slider.getBounds().x + game.slider.getBounds().width - 1 &&
                            y > game.slider.getBounds().y - ballDiameter &&
                            y < game.slider.getBounds().y + game.slider.getBounds().height) {
                        System.out.println("Go Right");
                        move_left = false;
                    }
                }

                // If the ball hits a brick
                notABrick hitBrick = brickCollision();
                if (hitBrick != null) {
                    if (x + ballDiameter >= hitBrick.getBounds().x &&
                            x <= hitBrick.getBounds().x + hitBrick.getBounds().width &&
                            y + ballDiameter - 1 <= hitBrick.getBounds().y) {
                        move_up = true;
                    } else if (x + ballDiameter >= hitBrick.getBounds().x && //
                            x <= hitBrick.getBounds().x + hitBrick.getBounds().width &&
                            y >= hitBrick.getBounds().y + hitBrick.getBounds().height - 1) {
                        move_up = false;
                    } else if (x <= hitBrick.getBounds().x &&
                            y > hitBrick.getBounds().y - ballDiameter &&
                            y < hitBrick.getBounds().y + hitBrick.getBounds().height) {
                        move_left = true;
                    } else if (x >= hitBrick.getBounds().x + hitBrick.getBounds().width - 1 &&
                            y > hitBrick.getBounds().y - ballDiameter &&
                            y < hitBrick.getBounds().y + hitBrick.getBounds().height) {
                        move_left = false;
                    }

                    if (hitBrick.reduceHealth() < 1) {
                        game.brickList.remove(hitBrick);
                    }
                }

                // If the ball hits a wall
                if (y > getHeight() - ballDiameter) {
                    game.gameOver = true;
                    return;
                }

                if (y < 0) {
                    move_up = false;
                }

                if (move_up) {
                    y -= 1;
                } else {
                    y += 1;
                }

                // Horizontal
                if (x > getWidth() - ballDiameter) {
                    move_left = true;
                }

                if (x < 0) {
                    move_left = false;
                }

                if (move_left) {
                    x -= 1;
                } else {
                    x += 1;
                }
                repaint();
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.red);
        g2d.fillOval(x, y, ballDiameter, ballDiameter);
        Toolkit.getDefaultToolkit().sync();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ballDiameter, ballDiameter);
    }

    private boolean sliderCollision() {
        return game.slider.getBounds().intersects(getBounds());
    }

    private notABrick brickCollision() {
        for (notABrick brick : game.brickList) {
            if (brick.getBounds().intersects(getBounds())) {
                return brick;
            }
        }
        return null;
    }
}