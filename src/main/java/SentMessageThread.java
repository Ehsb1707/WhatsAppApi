import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class SentMessageThread extends MyRunnable{
    private ArrayList<MessageWhatsapp> whatsappArrayList;
    private final LoginThread loginThread ;
    private static final String Error = "Phone number shared via incorrect link";
    private static final String ERROR_MESSAGE = "_2Nr6U";
    private static final String BUTTON_ERROR_CSS_SELECTOR = "div[role='button'][tabindex='0'][class='_20C5O _2Zdgs']" ;
    private static final String SEND_MESSAGE_TAB = "footer";
    private static final String CSS_BOX_TEXT = "div[role='textbox']";
    private static final String CSS_BUTTON_MESSAGE = "button[class='tvf2evcx oq44ahr5 lb5m6g5c svlsagor eqia9gcq'";

    public SentMessageThread (StartSystemDriver startSystemDriver , ArrayList<MessageWhatsapp> list , LoginThread loginThread) {
        super(startSystemDriver);
        this.loginThread = loginThread ;
        this.whatsappArrayList = list ;
    }

    public void _run() {
        if (!loginThread.isRunning()) {
            for (MessageWhatsapp message : whatsappArrayList) {
                if (!message.isSent() && message.getSentType() != MessageWhatsapp.ERROR_STATUS_INT){
                    UtilJ.sleep(Constants.SEC);
                    getStart().setDriverPage(message.getFormatPhoneNumber());
                    do {
                        sendMessage(message);
                        UtilJ.sleep(Constants.SEC * 2) ;
                    }while (!message.isSent() && message.getSentType() != MessageWhatsapp.ERROR_STATUS_INT);
                }
            }
        }
        assert  this.whatsappArrayList != null;
        whatsappArrayList = getStart().removeAllErrors();
        if (whatsappArrayList.stream().allMatch(MessageWhatsapp::isSent))
            stop();
    }

    public void stop() {
        super.stop();
        StatusMessageThread statusMessageThread = new StatusMessageThread(getStart() , this.whatsappArrayList);
        new Thread(statusMessageThread).start();
    }

    public void sendMessage (MessageWhatsapp messageWhatsapp) {
        if (!messageWhatsapp.isSent() && !errorNumber(messageWhatsapp)) {
            WebElement text ;
            try {
                text = getStart().getDriver().findElement(By.tagName(SEND_MESSAGE_TAB));
                WebElement textBox = text.findElement(By.cssSelector(CSS_BUTTON_MESSAGE));
                textBox.sendKeys(messageWhatsapp.getMessage());
                WebElement clicked = text.findElement(By.cssSelector(CSS_BUTTON_MESSAGE));
                if(clicked != null) {
                    clicked.click();
                    messageWhatsapp.setSentType(MessageWhatsapp.DELIVERED);
                }
                this.getStart().setJLabelStatusByMessage(messageWhatsapp);
            } catch (Exception ignore) {
            }
        }
    }

    public boolean errorNumber (MessageWhatsapp messageWhatsapp) {
        boolean error = false ;
        try {
            WebElement errorClass = getStart().getDriver().findElement(By.className(ERROR_MESSAGE));
            if (errorClass.getText().contains(Error)){
                WebElement buttonError = getStart().getDriver().findElement(By.cssSelector(BUTTON_ERROR_CSS_SELECTOR));
                if (buttonError != null) {
                    UtilJ.sleep(1);
                    buttonError.click();
                    getStart().addErrorMessage(messageWhatsapp);
                    messageWhatsapp.setSentType(MessageWhatsapp.ERROR_STATUS_INT);
                    getStart().setJLabelStatusByMessage(messageWhatsapp);
                    error = true ;
                }
            }
        }catch (Exception ignore){
        }
        return error;
    }
}
