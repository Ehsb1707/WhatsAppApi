public class WindowConfirmation extends BaseFrame{

    public WindowConfirmation () {
        super(Constants.WINDOW_W , Constants.WINDOW_H);
        MessageConfirmation confirmation = new MessageConfirmation() ;
        add(confirmation);
        setVisible(true);
    }

    public static void main(String[] args) {
        WindowConfirmation window = new WindowConfirmation();
    }
}
