import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private Player player;
    private ArrayList<NumberObject> numbers;
    private Timer gameTimer;
    private Timer timeCounter;
    private ArrayList<Bomb> bombs;
    private boolean gameEnded = false;
    private int level = 1;
    private final int MAX_LEVEL = 5;

    private int score = 0;
    private int timeLeft = 60;
    private int targetNumber;

    private Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(WIDTH / 2, HEIGHT - 60);
        numbers = new ArrayList<>();
        bombs = new ArrayList<>();
        targetNumber = random.nextInt(90) + 10;
        setupLevel();


        gameTimer = new Timer(30, this);
        gameTimer.start();

        timeCounter = new Timer(1000, e -> {
            if (timeLeft > 0) timeLeft--;
        });
        timeCounter.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Target: " + targetNumber, 20, 30);
        g.drawString("Score: " + score, 20, 60);
        g.drawString("Time: " + timeLeft, 480, 30);
        g.drawString("Level: " + level, 480, 60);

        player.draw(g);

        for (NumberObject n : numbers) {
            n.draw(g);
        }
        
        for (Bomb b : bombs) {
            b.draw(g);
        }

        if (timeLeft <= 0 && !gameEnded) {

           
            gameTimer.stop();
            timeCounter.stop();
            gameEnded = true;
        }
        if (gameEnded) {
          
            if (targetNumber <= 0) {

                level++;

                if (level > MAX_LEVEL) {
                    g.setFont(new Font("Arial", Font.BOLD, 40));
                    g.setColor(Color.GREEN);
                    g.drawString("YOU WIN!", 200, 300);
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.setColor(Color.WHITE); 
                    g.drawString("All levels Completed!", 200, 340);
                    g.drawString("Score : " + score, 200, 380);
                  
                }
                
            } else {
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.setColor(Color.RED);
                g.drawString("YOU LOST!", 200, 300);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(Color.WHITE);
                g.drawString("Target Failed!", 200, 340);
                g.drawString("Score : " + score, 200, 380);
            }
        }
      


    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (random.nextInt(100) < (4 + level)){
            numbers.add(new NumberObject(random.nextInt(WIDTH - 30), 0));
        }
    	if (random.nextInt(100) < ( level*3)) {
    	    bombs.add(new Bomb(random.nextInt(WIDTH - 25), 0));
    	}

        
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            b.move();
            
            

            if (level==2)b.setSpeedBomb(8);
            else if (level==3)b.setSpeedBomb(11);
            else if (level==4)b.setSpeedBomb(14);
            else if (level==5)b.setSpeedBomb(17);

            if (b.getBounds().intersects(player.getBounds())) {
                score -= 20;
                timeLeft-=5;
                bombs.remove(i);
                break;
            }

            if (b.getY() > HEIGHT) {
                bombs.remove(i);
                break;
            }
        }


        for (int i = 0; i < numbers.size(); i++) {
            NumberObject n = numbers.get(i);
            n.move();

            if (n.getBounds().intersects(player.getBounds())) {
                if (n.getValue() <= targetNumber) {
                    targetNumber -= n.getValue();
                    score += 10;
                    timeLeft +=1;
                } else {
                    score -= 5;
                }
                numbers.remove(i);
                break;
            }

            if (n.getY() > HEIGHT) {
                numbers.remove(i);
                break;
            }
        }
      
        if (targetNumber <= 0) {

            level++;

            if (level > MAX_LEVEL) {
              timeLeft = 0;
              
              
            } else {
                setupLevel();
            }
            
        } 


        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            player.moveLeft();
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            player.moveRight();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    
    private void setupLevel() {

        numbers.clear();
        bombs.clear();
        
        
        switch (level) {
            case 1:
                targetNumber = random.nextInt(40) + 10; 
                break;

            case 2:
                targetNumber = random.nextInt(60) + 20;
                timeLeft +=score/5;
                
                break;

            case 3:
                targetNumber = random.nextInt(80) + 30;
                timeLeft +=score/10;
                break;

            case 4:
                targetNumber = random.nextInt(100) + 40;
                timeLeft +=score/15;
                break;

            case 5:
                targetNumber = random.nextInt(120) + 50;
                timeLeft +=score/20;
                break;
        }
    }

}
