import javax.swing.*;
import java.awt.*;

public class BaseJPanel extends JPanel {
    private ImageIcon backGround;
    private JLabel title;

    public BaseJPanel (int x, int y, int width, int height, Color color, String title) {
        setBounds(x,y,width,height);
        setBackground(color);
        this.backGround = null;
        init();
    }

    public BaseJPanel (int x, int y, int width, int height, String fieldName, String title) {
        setBounds(x,y,width,height);
        this.title = addJLabel(title, 0, 0, this.getWidth(), Constants.SIZE, Constants.SIZE / 2, Color.blue.brighter());
        this.title.setOpaque(true);
        this.backGround = new ImageIcon(fieldName);
        init();
    }

    public BaseJPanel (int x, int y, int width, int height , String fieldName) {
        setBounds(x,y,width, height);
        this.backGround = new ImageIcon(fieldName);
        init();
    }

    public JTextField addJTextField (String title , int y) {
        JLabel jLabel = addJLabel(title, 0, y, Constants.BUTTON_W, Constants.BUTTON_H, 15, Color.black);
        jLabel.setForeground(Color.CYAN.darker());
        return addTextField("",jLabel.getX()+jLabel.getHeight()+1 ,y,Constants.BUTTON_W, Constants.BUTTON_H);
    }

    public JTextField addJTextFieldWithTitleBlowAnotherTextField (JTextField textField, String title, int y) {
        JLabel label = addJLabel(title, 0 ,y, Constants.BUTTON_W , Constants.BUTTON_H , 15 , Color.black);
        label.setForeground(Color.CYAN.darker());
        return addTextFieldBelowAntherTextField(textField);
    }

    public void init () {
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.backGround != null)
            this.backGround.paintIcon(this,g,0,0);
    }

    public JLabel addJLabel (String title , int x, int y, int w, int h, int size , Color color) {
        JLabel jLabel = new JLabel(title , SwingConstants.CENTER);
        jLabel.setFont(new Font("arial", Font.BOLD, size));
        jLabel.setForeground(color);
        jLabel.setBounds(x,y,w,h);
        this.add(jLabel);
        return jLabel;
    }

    public void setBackGround(String path) {
        if (this.backGround != null) {
            backGround = new ImageIcon(path);
            repaint();
        }
    }

    public JLabel addLabelBelowAntherLabel(JLabel other, String string, int size) {
        return addJLabel(string, other.getX(), other.getY() + other.getHeight(), other.getWidth(), other.getHeight(), size, other.getForeground());
    }

    public JLabel addLabelNextAntherLabel(JLabel other, String string, int size) {
        return addJLabel(string, other.getX() + other.getWidth(), other.getY(), other.getWidth(), other.getHeight(), size, other
                .getForeground());
    }

    public JTextField addTextField(String title, int x, int y, int w, int h) {
        JTextField text = new JTextField(title, SwingConstants.CENTER);
        text.setBounds(x, y, w, h);
        this.add(text);
        return text;
    }

    public JTextField addTextFieldBelowAntherTextField(JTextField other) {
        return addTextField(null, other.getX(), other.getY() + other.getHeight(), other.getWidth(), other.getHeight());
    }

    public JTextField addTextFieldNextAntherTextField(TextField other, String string) {
        return addTextField(string, other.getX() + other.getWidth(), other.getY(), other.getWidth(), other.getHeight());
    }

}
