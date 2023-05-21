public class LoginThread extends MyRunnable{
    private static final String PIC_PATH = "52141889_101.jpg";
    private static final String SYSTEM_MESSAGE = "Login success";

    public LoginThread (StartSystemDriver startSystemDriver) {
        super(startSystemDriver);
    }

    @Override
    public void _run() {
        if (getStart().login()) {
            getStart().setBackGround(PIC_PATH);
            getStart().getSysMessage().setText(SYSTEM_MESSAGE);
            getStart().setMessageList();
            stop();
        }
    }


}
