import java.awt.BorderLayout;  
import java.awt.EventQueue;  
import java.awt.Graphics;
import java.awt.Image;  
import java.awt.Toolkit;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
import javax.swing.border.EmptyBorder;  
public class MyFrame extends JFrame {  
    private JPanel contentPane;  
    /**  
     * Launch the application.  
     */  
    public static void main(String[] args) {  
    	EventQueue.invokeLater(new Runnable() {  
    		public void run() {  
    			try {  
    				MyFrame frame = new MyFrame();  
                           frame.setVisible(true);  
                      } catch (Exception e) {  
                           e.printStackTrace();  
                      }  
                 }  
            });  
       }  
       /**  
        * Create the frame.  
        */  
    // generate a JFrame with background image 
       public MyFrame() {  
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            setBounds(200, 50, 1100, 740);  
            contentPane = new JPanel() {  
                 public void paintComponent(Graphics g) {  
                      Image img = Toolkit.getDefaultToolkit().getImage(  
                                MyFrame.class.getResource("/levelImage/levelBackground.jpg"));  
                      g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
                 }  
            };  
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
            contentPane.setLayout(new BorderLayout(0, 0));  
            setContentPane(contentPane);  
       }  
  }  