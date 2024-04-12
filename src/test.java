import java.awt.*;
import java.awt.event.KeyEvent;

public class test {
    public static void main(String[] args) {
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(1000,384);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
