
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class snake extends JPanel implements ActionListener, KeyListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private static final int UNIT_SIZE = 15;
    private static final int ALL_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int INITIAL_DELAY = 100;
    private final int x[] = new int[ALL_UNITS];
    private final int y[] = new int[ALL_UNITS];
    private int bodyParts = 6;
    private int foodX, foodY;
    private int score = 0;
    private static int highScore = 0;
    private char direction = 'R';
    private boolean running = false;
    private boolean paused = false;
    private Timer timer;
    private Random random;

    public snake() {
        random = new Random();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

    private void startGame() {
        newFood();
        running = true;
        paused = false;
        direction = 'R';
        bodyParts = 6;
        score = 0;
        timer = new Timer(INITIAL_DELAY, this);
        timer.start();
    }

    private void newFood() {
        foodX = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' ->
                y[0] -= UNIT_SIZE;
            case 'D' ->
                y[0] += UNIT_SIZE;
            case 'L' ->
                x[0] -= UNIT_SIZE;
            case 'R' ->
                x[0] += UNIT_SIZE;
        }
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            bodyParts++;
            score++;
            if (score > highScore) {
                highScore = score;
            }
            newFood();
            int newDelay = Math.max(50, INITIAL_DELAY - score * 2);
            timer.setDelay(newDelay);
        }
    }

    private void checkCollisions() {
        // wall collisions
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }
        // self collisions
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        if (!running) {
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        if (running) {
            drawFood(g);
            drawSnake(g);
            drawScore(g);
            if (paused) {
                drawPause(g);
            }
        } else {
            drawGameOver(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(40, 40, 40));
        for (int i = 0; i <= WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
        }
        for (int i = 0; i <= HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.GREEN.darker());
            } else {
                g.setColor(new Color(45, 180, 0));
            }
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("Score: " + score + "   High Score: " + highScore, 10, 25);
    }

    private void drawPause(Graphics g) {
        g.setColor(new Color(255, 255, 255, 150));
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        String msg = "PAUSED";
        g.drawString(msg, (WIDTH - g.getFontMetrics().stringWidth(msg)) / 2, HEIGHT / 2);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        String over = "Game Over";
        g.drawString(over, (WIDTH - g.getFontMetrics().stringWidth(over)) / 2, HEIGHT / 2 - 20);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        String scr = "Score: " + score;
        g.drawString(scr, (WIDTH - g.getFontMetrics().stringWidth(scr)) / 2, HEIGHT / 2 + 20);
        String restart = "Press ENTER to Restart";
        g.drawString(restart, (WIDTH - g.getFontMetrics().stringWidth(restart)) / 2, HEIGHT / 2 + 60);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if (direction != 'R') {
                    direction = 'L';

                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != 'L') {
                    direction = 'R';

                }
            }
            case KeyEvent.VK_UP -> {
                if (direction != 'D') {
                    direction = 'U';

                }
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != 'U') {
                    direction = 'D';

                }
            }
            case KeyEvent.VK_SPACE -> {
                if (running) {
                    paused = !paused;

                }
            }
            case KeyEvent.VK_ENTER -> {
                if (!running) {
                    startGame();

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game Advanced");
            frame.add(new snake());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
