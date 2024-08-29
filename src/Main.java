import UI.FrameManager;

public class Main {

    public static void main(String [] args) {
        FrameManager manager = new FrameManager();
        manager.setManager();
        manager.buildLogin();
        manager.showFrame();
    }
}
