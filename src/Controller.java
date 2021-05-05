import javax.swing.*;

public class Controller {
    private int sizeProgress;
    private View view;
    private int valueProgress;
    private static Controller controller;

    private Controller(View view) {
        this.view = view;
    }

    public static Controller getController(View view) {
        if (controller == null) {
            controller = new Controller(view);
            return controller;
        }
        return controller;
    }

    public int getValueProgress() {
        return valueProgress;
    }

    public void setSizeProgress(int sizeProgress) {
        view.getProgressBar().setMaximum(sizeProgress);
    }

    public void setValueProgressProgressBar(int row) {
        view.changeProgressBar(row);
    }
}
