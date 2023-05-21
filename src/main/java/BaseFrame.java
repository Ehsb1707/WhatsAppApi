import javax.swing.*;

public class BaseFrame extends JFrame {
    public BaseFrame (int w , int h) {
        this.setSize(w, h);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
