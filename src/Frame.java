import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {
    Robot robot;

    boolean state = false, button_clicked = false;
    Frame(){
        setTitle("AutoClicker");
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200,200);
        JButton startButton = new JButton("Start");
        thread.start();
        startButton.addActionListener(e -> {
            button_clicked = !button_clicked;
            System.out.println(state);
        });
        setLayout(new BorderLayout());
        add(startButton,BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
                System.out.println("Gainsssss");
                state = false;
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                super.windowLostFocus(e);
                System.out.println("Losssss");
                state = true;
            }
        });
    }

    Thread thread = new Thread(()->{
        while(true){
            try {
                robot = new Robot();
                if (state && button_clicked){
                    robot.mousePress(KeyEvent.BUTTON1_MASK);
                    System.out.println("hfehfe");
                    robot.keyPress(KeyEvent.VK_W);
                }
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    });
}
