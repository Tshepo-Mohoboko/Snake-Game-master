package tshepo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Snake extends JPanel implements ActionListener {

    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 2500;
    private final int RAND_POS = 49;
    private final int DELAY = 80;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inMenu = true;
    private boolean inGame = false;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image background;
    private Image menu;
    private Audio backgroundMusic;
    private Audio gameOverMusic;
    private Audio levelUp;

    public Snake(){
        initiateSnake();
    }

    private void initiateSnake() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        cargarImagenes();
        initiatetshepo();
        backgroundMusic = new Audio("/resources/background.mp3");
        gameOverMusic = new Audio("/resources/abucheo.mp3");
        backgroundMusic.play();
        levelUp = new Audio("/resources/levelup.mp3");
    }


    private void cargarImagenes() {

        ImageIcon iid = new ImageIcon("src/resources/cuerpo.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/manzanita.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();

        ImageIcon iib = new ImageIcon("src/resources/background.png");
        background = iib.getImage();

        ImageIcon iim = new ImageIcon("src/resources/menu.jpg");
        menu = iim.getImage();
    }

    private void initiatetshepo() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inMenu){
         menu(g);
        }

        if (inGame) {
            g.drawImage(background, 0, 0, this);
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            if (inGame != true && inMenu != true){
                gameOver(g);
            }
        }
    }
    private void menu(Graphics g) {

        g.drawImage(menu, -225, 0, this);
        String msg = "Press Enter to play";
        String msg2 = "Sofia Aguirre, Brian Orange";
        Font small = new Font("Helvetica", Font.BOLD, 35);
        Font names = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(names);
        g.drawString(msg2, (WIDTH - metr.stringWidth(msg2)) / 10, HEIGHT / 100 + 10);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT - 15);
    }
    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 35);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
        backgroundMusic.stop();
        gameOverMusic.play();
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            levelUp.levelUp();
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_ENTER)) {
                inMenu = false;
                inGame = true;
            }

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
