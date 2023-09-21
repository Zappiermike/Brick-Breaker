import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class BrickBreaker extends JFrame implements KeyListener {

    JLabel sliderLabel;
    JLabel backgroundLabel;
    ImageIcon icon;

    public BrickBreaker() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 600);
        this.setLayout(null);
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);
        
        URL backgroundUrl = BrickBreaker.class.getResource("bb_background.jpg");
        ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        
        sliderLabel = new JLabel();
        sliderLabel.setBounds(200, 500, 100, 30);
        sliderLabel.setOpaque(true);
        URL url = BrickBreaker.class.getResource("blueslider.png");
        icon = new ImageIcon(url);
        sliderLabel.setIcon(icon);
        this.add(new Ball());
        // this.add(sliderLabel);
        // this.add(backgroundLabel);
        this.setVisible(true);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int speed = 20;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("You Pressed Up!");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("You Pressed Down!");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("You Pressed Left!");
                if (sliderLabel.getX() - speed >= 0) {
                    sliderLabel.setLocation(sliderLabel.getX() - speed, sliderLabel.getY());
                }
                System.out.println("labelx: " + sliderLabel.getX() + " thisx: " + this.getX());
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("You Pressed Right!");
                if (sliderLabel.getX() + 100 < this.getSize().getWidth()) {
                    sliderLabel.setLocation(sliderLabel.getX() + speed, sliderLabel.getY());
                }
                System.out.println("labelx: " + sliderLabel.getX() + " thisx: " + this.getSize().getWidth());
                break;
        }
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