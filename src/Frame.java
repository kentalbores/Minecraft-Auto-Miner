import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import static com.github.kwhat.jnativehook.GlobalScreen.addNativeMouseListener;

public class Frame extends JFrame {
    Robot robot;
    int mode = 0;

    boolean state = false, button_clicked = false;
    Frame(){
        setTitle("AutoClicker");
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200,200);
        setAlwaysOnTop(true);
        JButton startButton = new JButton("Start [MMB]");
        JButton[] autoMode = new JButton[3];

        autoMode[0] = new JButton("Mine");
        autoMode[1] = new JButton("XPFarm");
        autoMode[2] = new JButton("Fish");

        thread.start();
        startButton.addActionListener(e -> canStart(startButton));

        setLayout(new GridLayout(0, 1));
        add(startButton);

        for(int i = 1; i < 4; i++){
            int finalI = i;
            autoMode[i-1].addActionListener(e -> {
                for(JButton buttons : autoMode){
                    buttons.setEnabled(true);
                }
                mode = finalI;
                autoMode[finalI-1].setEnabled(false);
            });
            add(autoMode[i-1]);
        }

        setLocationRelativeTo(null);
        setVisible(true);
        addNativeMouseListener(new NativeMouseListener(){
            public void nativeMouseClicked(NativeMouseEvent e) {
                if(e.getButton() == 3) { // middle click to start shortcut
                    canStart(startButton); //note: native mouse 3 is mmb while robot is 2
                }                          // and vice versa for rmb

                System.out.print("hi");
            }
        });
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
    public void canStart(JButton startBtn){
        if(mode > 0){
            button_clicked = !button_clicked;
            startBtn.setText(button_clicked ? "Stop [MMB]" : "Start [MMB]");
            System.out.println(state);
        }
        else
            JOptionPane.showMessageDialog(this,"Choose a mode first!");
    }

    Thread thread = new Thread(()->{
        while(true){
            try {
                robot = new Robot();
                Color mcIcon = new Color(160, 233, 117);
                boolean isMC = mcIcon.equals(robot.getPixelColor(12,8));

                if (state && button_clicked && isMC){
                    if(mode == 1) {
                        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                        System.out.println("mine");
                        robot.keyPress(KeyEvent.VK_W);
                        System.out.println(robot.getPixelColor(12, 8)); //ilisi ang mcIcon if lahi imoha
                        Thread.sleep(10);
                    }
                    else if(mode == 2){
                        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

                        System.out.println("farm");
                        Thread.sleep(1000);
                    }
                    else if(mode == 3){
                        robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK); //right click
                        System.out.println("fish");
                        Thread.sleep(500);
                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    });
}