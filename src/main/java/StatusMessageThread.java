import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class StatusMessageThread extends MyRunnable{
    public static final String SENT_1 = "msg-check";
    public static final String SENT_2 = "msg-dblcheck";
    public static final String CLASS_OF_READ_MESSAGE = "_314_3";
    public static final String CSS_STATUS_MESSAGE = "div[class='do8e0lj9 l7jjieqr k6y3xtnu']";
    public static final String TAG_STATUS_MESSAGE = "span";
    public static final String ATTRIBUTE_STATUS_MESSAGE = "data-testid";
    public static final String MESSAGE_OUT_CLASS = "message-out";
    private boolean checked ;
    private int sec ;
    private final ArrayList<MessageWhatsapp> messageList ;

    public StatusMessageThread (StartSystemDriver startSystem , ArrayList<MessageWhatsapp> list) {
        super(startSystem);
        this.messageList = list;
        this.checked = false ;
        if (messageList.size() != 0 ) {
            this.sec = 10 / messageList.size();
        } else stop();
    }

    @Override
    public void _run () {
        for (MessageWhatsapp message : this.messageList) {
            if (message.getResponse() == null ) {
                getStart().setDriverPage(message.getFormatPhoneNumber());
                do {
                    statusMessage(message);
                    UtilJ.sleep(Constants.SEC * 2);
                } while (!checked);
            }
            checked = false ;
        }
        if (messageList.stream().allMatch(messageWhatsapp -> messageWhatsapp.getResponse() != null) )
            stop();
    }

    public void statusMessage (MessageWhatsapp message) {
        try {
            List<WebElement> allMessages = getStart().loadMessagesList();
            if (allMessages.size() > 0) {
                WebElement lastMessage = allMessages.get(allMessages.size()-1);
                WebElement myMessage = getMyMessages(allMessages);

                if (myMessage != null) {
                    WebElement css = myMessage.findElement(By.cssSelector(CSS_STATUS_MESSAGE));
                    WebElement Seen = css.findElement(By.tagName(TAG_STATUS_MESSAGE));
                    String find = Seen.getAttribute(ATTRIBUTE_STATUS_MESSAGE);

                    if (find.equals(SENT_1)) {
                        message.setSentType(MessageWhatsapp.SENT_STATUS_INT);
                    } else if (find.equals(SENT_2)) {
                        message.setSentType(MessageWhatsapp.ACCEPTED_STATUS_INT);
                        String className = Seen.getAttribute(Constants.CLASS);
                        if (className.equals(CLASS_OF_READ_MESSAGE))
                            message.setSentType(MessageWhatsapp.STATUS_SEEN_INT);
                    }
                }
                if (lastMessage != null && !isSentThisMessage(lastMessage))
                    message.setResponse(lastMessage.getText());

                getStart().setJLabelStatusByMessage(message);
                checked = true ;

            }
        } catch (Exception ignored) {
        }
    }

    public boolean isSentThisMessage (WebElement element) {
        boolean sent = false ;
        if (element != null) {
            String classSent = element.getAttribute(Constants.CLASS);
            if (classSent.contains(MESSAGE_OUT_CLASS))
                sent = true ;
        }

        return sent;
    }

    public WebElement getMyMessages (List<WebElement> elements) {
        WebElement mine = null ;
        for (int i = elements.size() -1 ; i >= 0 ; i --) {
            if (isSentThisMessage(elements.get(i))){
                mine = elements.get(i);
                break;
            }
        }
        return mine ;
    }
}
