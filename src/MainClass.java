import java.io.IOException;

public class MainClass {
    public static void main(String[] args) {
        View view = View.getView();
        Controller.getController(view);
    }
}
