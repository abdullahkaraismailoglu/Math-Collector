import java.awt.*;
import java.util.Random;

public class NumberObject {

    private int x, y;
    private int value;
    private final int speed = 2;

    public NumberObject(int x, int y) {
        this.x = x;
        this.y = y;
        value = new Random().nextInt(9) + 1;
    }

    public void move() {
        y += speed;
    }

    public int getValue() {
        return value;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 25, 25);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawRect(x, y, 25, 25);
        g.drawString(String.valueOf(value), x + 8, y + 18);
    }
}
