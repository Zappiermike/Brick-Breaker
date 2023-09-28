import javax.swing.JComponent;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class Brick extends JComponent {

    private int x;
    private int y;
    private int width = 94;
    private int height = 30;
    private int health = 1;

    public Brick(int startingX, int startingY) {
        this.x = startingX;
        this.y = startingY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int reduceHealth(){
        this.health--;
        if (this.health < 1){
            System.out.println("Brick has zero health! Disappearing!");
            setVisible(false);
        }
        return this.health;
    }

}