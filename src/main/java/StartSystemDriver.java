import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class StartSystemDriver extends BaseJPanel{
    public static final String ALL_MESSAGE_CLASS = "_2wUmf";
    public static final String GET_DRIVER = "https://web.whatsapp.com/send?phone=972";
    private static final String STATUS_MESSAGE_START = "Status Message -> The message is loading - not sent yet";
    private static final String MESSAGE_START = "Try to connect ! You need to scan the QR";
    private static final String REPORT_TITLE_BUTTON = "Click here for making a report message";
    private static final String LOGIN_CLASS = "1dL67";
    private static final String WHATSAPP_PATH = "https://web.whatsapp.com/";
    private static final String PIC_PATH = "QR.png";
    private ChromeDriver driver ;
    private JLabel sysMessage ;
    private final ArrayList<MessageWhatsapp> messageList ;
    private final ArrayList<MessageWhatsapp> errorList ;
    private final HashMap<MessageWhatsapp , JLabel> statusMessageList ;

    public StartSystemDriver (ArrayList<MessageWhatsapp> messageList) {
        super(0 , 0 , Constants.WINDOW_W , Constants.WINDOW_H , PIC_PATH );
        this.messageList = messageList;
        this.errorList = new ArrayList<>();
        this.statusMessageList = new HashMap<>();
        getIn();
    }

    public void addErrorMessage (MessageWhatsapp error) {
        this.errorList.add(error);
    }

    public ArrayList<MessageWhatsapp> removeAllErrors () {
        this.messageList.removeAll(errorList);
        return this.messageList;
    }

    public void setMessageList () {
        int y = 100 ;
        initReportButton() ;
        int distance = (Constants.WINDOW_H - y * 3) / messageList.size();
        for (MessageWhatsapp messageWhatsapp : messageList) {
            statusMessageList.put(messageWhatsapp , addJLabel(messageWhatsapp.getFormatPhoneNumber() + STATUS_MESSAGE_START, 0, y, Constants.WINDOW_W, Constants.MESSAGE_H, Constants.FONT_SIZE, Color.blue));
            y += distance ;
        }
        repaint();
    }

    public JLabel getSysMessage () { return this.sysMessage ;}

    public void setDriverPage (String phoneNumber) {
        this.driver.get(GET_DRIVER + phoneNumber.substring(1));
        while (!this.login())
            UtilJ.sleep(1);
    }

    public List<WebElement> loadMessagesList() { return (List<WebElement>) this.driver.findElements(By.className(ALL_MESSAGE_CLASS));}


    public ChromeDriver getDriver() { return this.driver;}

    public void run() {
        LoginThread loginThread = new LoginThread(this);
        new Thread(loginThread).start();
        SentMessageThread messageThread = new SentMessageThread(this , this.messageList, loginThread);
        new Thread(messageThread).start();
    }

    public void setJLabelStatusByMessage (MessageWhatsapp message) {
        this.statusMessageList.get(message).setText(message.getFormatPhoneNumber() + " " + message.getStatus());
        this.repaint();
    }

    public void initDriver () {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\97252\\Downloads\\chromedriver_win32 (1)\\1\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-fata-dir=C:\\Users\\97252\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(WHATSAPP_PATH);
        UtilJ.sleep(Constants.SEC * 2);
        sysMessage = addJLabel(MESSAGE_START, 0, 0, Constants.WINDOW_W, Constants.SIZE, 20, Color.blue);
    }

    public void initReportButton () {
        Button summaryReport = new Button(REPORT_TITLE_BUTTON);
        summaryReport.setBounds(0 , Constants.WINDOW_H - Constants.SIZE , Constants.WINDOW_W , Constants.SIZE / 2);
        summaryReport.addActionListener(e -> {
            writeToFile();
        });

        summaryReport.setBackground(Color.lightGray);
        summaryReport.setForeground(Color.white);
        summaryReport.setFont(Constants.FONT);
        this.add(summaryReport);
    }

    public boolean login() {
        List<WebElement> in = new LinkedList<>();
        try {
            in = driver.findElements(By.className(LOGIN_CLASS));

        } catch (Exception ignored) {

        }
        return in.size() != 0;
    }

    public void getIn () {
        initDriver();
        run();
    }

    public void writeToFile () {
        FileWriter fileWriter ;
        try {
            fileWriter = new FileWriter(Constants.PATH);
            Set<MessageWhatsapp> set = new HashSet<>();
            set.addAll(messageList);
            set.addAll(errorList);
            fileWriter.write(set.toString());
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
