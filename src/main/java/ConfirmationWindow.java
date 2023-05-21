public class ConfirmationWindow extends BaseFrame{

    public ConfirmationWindow () {
        super(Constants.WINDOW_W , Constants.WINDOW_H);
        MessageConfirmation messageConfirmation = new MessageConfirmation();
        add(messageConfirmation);
        setVisible(true);
    }

    public static void main(String[] args) {
        ConfirmationWindow confirmationWindow = new ConfirmationWindow();
    }
}
