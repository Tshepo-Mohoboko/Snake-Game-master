package tshepo;
import java.awt.*;
import javax.swing.JFrame;

public class GamePanel extends JFrame {

    public GamePanel() {
        initUI();
    }
    private void initUI() {
        add(new Snake());
        setResizable(false);
        pack();
        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new GamePanel();
            ex.setVisible(true);
        });
    }
}

