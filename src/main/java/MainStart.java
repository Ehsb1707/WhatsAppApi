public class MainStart extends BaseFrame{
    public MainStart (StartSystemDriver panel) {
        super(Constants.WINDOW_W , Constants.WINDOW_H);
        add(panel);
        setVisible(true);
    }
}
