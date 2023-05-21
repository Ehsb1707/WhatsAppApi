public abstract class MyRunnable implements Runnable{
    private boolean running = true ;
    private final StartSystemDriver startSystemDriver ;

    public MyRunnable (StartSystemDriver systemDriver) {
        this.startSystemDriver = systemDriver;
    }

    public StartSystemDriver getStart() {
        return startSystemDriver;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public abstract void _run () ;

    @Override
    public void run () {
        while (running)
            this._run();
    }
}
