import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import java.awt.Rectangle;

public class Slider extends JComponent implements KeyListener {

    private int x;
    private final int y;
    private int width;
    private int height;
    private BrickBreaker game;

    public Slider(BrickBreaker game, int x, int y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int speed = 20;
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (x - speed >= 0) {
                    x -= speed;
                    repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (x + speed <= getWidth() - width) {
                    x += speed;
                    repaint();
                }
                break;
        }
    }

    public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println();
    }
}