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
    int x = 183; // Starting x coordinate
    int y = 533; // Starting y coordinate
    final int ballDiameter = 20;
    int frameBoundX, frameBoundY;
    boolean move_up = true;
    boolean move_left = false;
    private BrickBreaker game;

    public Ball(BrickBreaker game, int xb, int yb) {
        this.frameBoundX = xb;
        this.frameBoundY = yb;
        this.game = game;

        // The timer is used to repaint the component.
        Timer timer = new Timer(4, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (collision()) {
                    System.out.println(String.format("Ball's coordinates: %d, %d\n"
                            + "Slider's coordinates %d, %d\n",
                            getBounds().x, getBounds().y,
                            game.slider.getBounds().x,
                            game.slider.getBounds().y));
                    System.out.println(String.format("x: %d, y: %d, sliderX: %d, sliderY: %d, balldiameter/1.5: %f", x,
                            y, game.slider.getBounds().x, game.slider.getBounds().y, (ballDiameter / 1.5)));
                    // Vertical now working
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

                    // System.out.println("\nMOVE UP Debug");
                    // System.out.println(String.format("%d >= %d\n%d <= %d\n%d < %d",
                    // x + ballDiameter, game.slider.getBounds().x,
                    // x, game.slider.getBounds().x + game.slider.getBounds().width,
                    // y + ballDiameter, game.slider.getBounds().y));

                    // System.out.println("\nMOVE LEFT Debug");
                    // System.out.println(String.format("%d <= %d\n%d > %d\n%d < %d",
                    // x, game.slider.getBounds().x,
                    // y, game.slider.getBounds().y - ballDiameter,
                    // y, game.slider.getBounds().y + game.slider.getBounds().height));

                    // System.out.println("\nMOVE RIGHT Debug");
                    // System.out.println(String.format("%d >= %d\n%d > %d\n%d < %d",
                    // x, game.slider.getBounds().x + game.slider.getBounds().width,
                    // y, game.slider.getBounds().y - ballDiameter,
                    // y, game.slider.getBounds().y + game.slider.getBounds().height));

                    // System.out.println("\nMOVE DOWN Debug");
                    // System.out.println(String.format("%d >= %d\n"
                    // + "%d <= %d\n"
                    // + "%d > %d", x + ballDiameter, game.slider.getBounds().x,
                    // x, game.slider.getBounds().x + game.slider.getBounds().width,
                    // y, game.slider.getBounds().y + game.slider.getBounds().height));
                }

                // Vertical handling
                if (y > getHeight() - ballDiameter) {
                    move_up = true;
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

        timer.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.red);
        g2d.fillOval(x, y, ballDiameter, ballDiameter);
        g2d.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    // public int getX(){
    // return x;
    // }

    // public int getY(){
    // return y;
    // }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ballDiameter, ballDiameter);
    }

    private boolean collision() {
        return game.slider.getBounds().intersects(getBounds());
    }

}