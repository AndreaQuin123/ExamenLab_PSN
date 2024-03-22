
package psn;

import codigo.GUI;
import java.io.IOException;
import javax.swing.JFrame;

public class PSN {

    public static void main(String[] args) throws IOException {

        GUI gui = new GUI();
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gui);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    
}
