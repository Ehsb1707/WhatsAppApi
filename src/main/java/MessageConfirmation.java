import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageConfirmation extends BaseJPanel{
    private static final String START_MESSAGE = "Enter valid Whatsapp message , and valid phone number split with ',' char ->";
    public static final int SIZE = 100 ;
    private static final String PIC_PATH = "gallery_6154_large.jpg";
    private static final String SYS_Message = "Whatsapp , Integrity check";
    private boolean valid ;
    private JTextField userTextFieldPhoneNumber ;
    private JTextField userTextMessage ;
    private JLabel messageToUser ;
    private final List<String> validPhoneNumber ;
    private final List<String> notValidPhoneNumber ;
    private String message ;
    private Button button;

    // constructor
    public MessageConfirmation () {
        super(0,0,Constants.WINDOW_W , Constants.WINDOW_H , PIC_PATH , SYS_Message);
        valid = false ;
        this.validPhoneNumber = new LinkedList<>();
        this.notValidPhoneNumber = new LinkedList<>();
        addButton();
        act();
    }

    public void act () {
        userTextFieldPhoneNumber = addJTextField("Insert a phone number and split with ',' ---->" , SIZE * 3);
        userTextMessage = addJTextFieldWithTitleBlowAnotherTextField(userTextFieldPhoneNumber , "Insert a message to sent ---->" , userTextFieldPhoneNumber.getY() + userTextFieldPhoneNumber.getHeight());
        messageToUser = addJLabel(START_MESSAGE,0 , userTextFieldPhoneNumber.getY() - userTextFieldPhoneNumber.getHeight(), Constants.WINDOW_W, userTextFieldPhoneNumber.getHeight(), 20, Color.white );
    }

    public void addButton () {
        this.button = new Button("Click to check if the input is valid !");
        this.button.setBounds(0 , Constants.WINDOW_W - Constants.SIZE , Constants.BUTTON_W , Constants.SIZE / 2);
        button.addActionListener( e -> {
            button.setVisible(false);
            messageToUser.setText("Your entered :" + notValidPhoneNumber.size() + validPhoneNumber.size() + "numbers are valid . Do you want to send the message to this list ? " );
            Button confirm = new Button("Confirm");
            confirm.setBounds(0, Constants.WINDOW_H - SIZE, Constants.BUTTON_W / 2 , SIZE / 2);
            confirm.addActionListener( e1 -> {
                confirm.setVisible(false);

                ArrayList<MessageWhatsapp> messageList =  new ArrayList<>();

                for (String validNumbers : validPhoneNumber)
                    messageList.add(new MessageWhatsapp(validNumbers , message ));

                StartSystemDriver driver = new StartSystemDriver(messageList);
                MainStart mainStart = new MainStart(driver);
                SwingUtilities.getAncestorOfClass(JFrame.class , this).setVisible(true);
                mainStart.setVisible(true);
            });

            Button cancel = new Button("Cancel");
            cancel.setBounds(confirm.getWidth(), Constants.WINDOW_H - SIZE , Constants.BUTTON_W / 2 , SIZE/2);
            cancel.addActionListener( e1 -> {
                button.setVisible(true);
                cancel.setVisible(false);
                confirm.setVisible(false);
                this.validPhoneNumber.clear();
                this.notValidPhoneNumber.clear();
            });
            this.add(cancel);
            this.add(confirm);
        });
        this.add(button);
    }

    public void validInput () {
        if (userTextMessage.getText().equals("")){
            messageToUser.setText("Empty input");
        } else {
            message = userTextMessage.getText();
        }

        String[] messages = userTextFieldPhoneNumber.getText().split(",");
        for (String phone : messages) {
            if (new PhoneParser(phone).checkInput()){
                validPhoneNumber.add(phone);
            } else notValidPhoneNumber.add(phone);
        }
        if (validPhoneNumber.size() > 0 && messages != null) {
            valid = true;
        } else  {
            validPhoneNumber.clear();
            notValidPhoneNumber.clear();
        }
    }

    public boolean isValid () {
        return this.valid;
    }
}
