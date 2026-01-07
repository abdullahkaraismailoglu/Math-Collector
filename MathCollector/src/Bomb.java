import java.awt.*;

public class Bomb {

    private int x, y;
    private final int size = 25;
    private int speed = 5;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
       
    }

    public void move() {
        y += speed;
    }

    public int getY() {
        return y;
    }
    

	public void setSpeedBomb(int speed) {
		this.speed = speed;
	}

	public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, size, size);

        g.setColor(Color.BLACK);
        g.drawString("X", x + 8, y + 17);
    }
}
