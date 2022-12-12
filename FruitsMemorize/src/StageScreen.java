import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.Color;

public class StageScreen extends JFrame {

	private JPanel basePane;
	private JLabel levelTitle;
	private JPanel dynamicGridPane;
	private String[] fruitsList;	// randomly generate fruitsList
	public static ArrayList<String> randomList;
	private ArrayList<FruitImageJLabel> fruitsImageList;
	private int random;
	private String fruit;
	private FruitImageJLabel fLabel;
	private int row, col;
	private JPanel commentPane;
	private JPanel lblPane;
	private JPanel progressPane;
	private JLabel lblMemorise;
	private JProgressBar progressBar;
	private JPanel progressLabel;
	private double time;
	private int[] numOfFruits;
	private Thread timer;
	private int level, difficulty;


	/**
	 * Create the frame.
	 */
	public StageScreen() {
		// get level and difficulty from the file "userChoose.txt"
		try {
			FileInputStream fileObject = new FileInputStream("userChoose.txt");
			Scanner reader = new Scanner(fileObject);
			
			if (reader.hasNext())
				level = Integer.parseInt(reader.nextLine());
			if (reader.hasNext())
				difficulty = Integer.parseInt(reader.nextLine());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setTitle("Fruit Game");
		// make a fruitList
		numOfFruits = new int[]{3, 5, 6, 8, 10, 11, 13, 15};
		fruitsList = new String[]{"Apple", "Banana", "Blueberry", "Cherry", "Lemon"};
		randomList = new ArrayList<String>();
		fruitsImageList = new ArrayList<FruitImageJLabel>();
		
		// timer set depends on the difficulty
		if (difficulty == 1)
			time = 10;
		else if (difficulty == 2)
			time = 7;
		else
			time = 5;
		
		// set the row and col to display fruitsList in matrix format
		row = (level/3 + 1); col = 5;
		
		// randomly generate a random fruit list
		for (int i = 0; i < numOfFruits[level-1]; i++) {
			// the type of fruits that appears depends on the level
			if (level <= 2) {
				random = new Random().nextInt(2);	// type of fruits: 2
			}
			else if (level <= 5) {
				random = new Random().nextInt(4);	// type of fruits: 4
			}
			else {
				random = new Random().nextInt(5);	// type of fruits: 5
			}
			randomList.add(fruitsList[random]);
			fruit = randomList.get(i);
			fLabel = new FruitImageJLabel(fruit,100,100);
			fruitsImageList.add(fLabel);
		}
		
		// write the file fruitsList.txt
		try {
			FileOutputStream fileStream = new FileOutputStream("fruitsList.txt", false);
			PrintWriter writer = new PrintWriter(fileStream);
			for (int i = 0; i < numOfFruits[level-1]; i++) {
				writer.println(randomList.get(i));
			}
			writer.close();
			fileStream.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		basePane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 1100, 740);
		basePane.setBackground(Color.WHITE);
		basePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(basePane);
		basePane.setLayout(new BorderLayout(0, 0));
		
		dynamicGridPane = new JPanel() {  
            public void paintComponent(Graphics g) {  // override method which makes a background image
                 Image img = Toolkit.getDefaultToolkit().getImage(  
                           MyFrame.class.getResource("/levelImage/EnableFrame.jpg"));  
                 g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
            }
       };
		dynamicGridPane.setBackground(Color.WHITE);
		dynamicGridPane.setLayout(new GridLayout(row, col, 0, 0));
		basePane.add(dynamicGridPane, BorderLayout.CENTER);		
		
		commentPane = new JPanel();
		basePane.add(commentPane, BorderLayout.SOUTH);
		commentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		lblPane = new JPanel();
		lblPane.setBackground(new Color(255, 228, 181));
		commentPane.add(lblPane);
		lblPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblMemorise = new JLabel("Memorise!");
		lblMemorise.setHorizontalAlignment(SwingConstants.CENTER);
		lblMemorise.setFont(new Font("Algerian", Font.PLAIN, 50));
		lblPane.add(lblMemorise);
		
		progressPane = new JPanel();
		commentPane.add(progressPane);
		progressPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBackground(Color.gray);
		progressBar.setForeground(Color.YELLOW);
		
		// adjust the progressbar font UI
		BasicProgressBarUI ui = new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.WHITE;
			}
			protected Color getSelectionForeground() {
				return Color.BLACK;
			}
		};
		progressBar.setUI(ui);
		progressBar.setFont(new Font("Bahnschrift", Font.PLAIN, 40));
		progressPane.add(progressBar);
		
		// display the random fruit list
		for (int i=0; i < numOfFruits[level-1]; i++)
			dynamicGridPane.add(fruitsImageList.get(i));
		
		TimerTask timerTask = new TimerTask();
		timer = new Thread(timerTask);
		timer.start();
	}
	
	
	// thread for a timer task
	public class TimerTask implements Runnable{
		@Override
		public void run() {
			// update the progressbar value at each 0.1 second
			for(double i = 1; i <= 10*time; i++) {
				progressBar.setValue((int) (100*(i/(time*10))));
				progressBar.setString(String.format("%.1f s", (time-i/10)));
				try {
					timer.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dispose();
			
			AnswerScreen answerFrame = new AnswerScreen();
			answerFrame.setVisible(true);
		}
		
	}
}

