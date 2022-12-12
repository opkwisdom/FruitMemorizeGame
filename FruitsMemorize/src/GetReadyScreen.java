import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class GetReadyScreen extends JFrame {

	private JPanel contentPane;
	private JLabel lblGetReady;
	private JPanel timerPanel;
	private JButton btnReady;
	private JLabel lblCountdown;
	private Thread CountDown;
	private JPanel textPanel;
	private JLabel lblLevel;
	private ImageIcon startbtn;
	private ImageIcon startbtnPressed;
	private int level, difficulty;
	private JButton btnBack;
	private String diff;


	/**
	 * Create the frame.
	 */
	public GetReadyScreen() {
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
		
		diff = new String();
		if (difficulty == 1)
			diff = "Easy";
		else if (difficulty == 2)
			diff = "Medium";
		else diff = "Hard";
		
		setTitle("Fruit Game");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 1100, 740);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		textPanel = new JPanel() {  
            public void paintComponent(Graphics g) {  // override method which makes a background image
                Image img = Toolkit.getDefaultToolkit().getImage(  
                          MyFrame.class.getResource("/levelImage/EnableFrame.jpg"));  
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
           }
        };
		contentPane.add(textPanel, BorderLayout.CENTER);
		textPanel.setLayout(new BorderLayout(0, 0));
		textPanel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		
		lblGetReady = new JLabel("Are you ready?");
		lblGetReady.setFont(new Font("Algerian", Font.PLAIN, 90));
		lblGetReady.setHorizontalAlignment(SwingConstants.CENTER);
		textPanel.add(lblGetReady);
		
		lblLevel = new JLabel(String.format("Level %d: %s", level, diff));
		lblLevel.setFont(new Font("Algerian", Font.PLAIN, 60));
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		textPanel.add(lblLevel, BorderLayout.NORTH);
		
		timerPanel = new JPanel();
		timerPanel.setBackground(Color.WHITE);
		contentPane.add(timerPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_timerPanel = new GridBagLayout();
		gbl_timerPanel.columnWidths = new int[] {215, 215, 215, 215, 215};
		gbl_timerPanel.rowHeights = new int[] {100, 50, 100};
		gbl_timerPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_timerPanel.rowWeights = new double[]{0.0, 0.0, 0.0};
		timerPanel.setLayout(gbl_timerPanel);
		
		lblCountdown = new JLabel("");
		lblCountdown.setFont(new Font("Algerian", Font.PLAIN, 70));
		GridBagConstraints gbc_lblCountdown = new GridBagConstraints();
		gbc_lblCountdown.insets = new Insets(0, 0, 5, 5);
		gbc_lblCountdown.gridx = 2;
		gbc_lblCountdown.gridy = 0;
		timerPanel.add(lblCountdown, gbc_lblCountdown);
		
		btnReady = new JButton("");
		startbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/startbutton.jpg"));
		startbtn = new ImageIcon(startbtn.getImage().getScaledInstance(215, 100, Image.SCALE_SMOOTH));
		btnReady.setIcon(startbtn);
		
		startbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/startbuttonPressed.jpg"));
		startbtnPressed = new ImageIcon(startbtnPressed.getImage().getScaledInstance(215, 100, Image.SCALE_SMOOTH));
		btnReady.setPressedIcon(startbtnPressed);
		btnReady.setDisabledIcon(startbtnPressed);
		btnReady.setBackground(Color.WHITE);
		btnReady.setMargin(new Insets(0,0,0,0));
		
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CountDownTask countdownTask = new CountDownTask();
				CountDown = new Thread(countdownTask);
				CountDown.start();
			}
		});
		
		btnBack = new JButton("");
		btnBack.setBackground(Color.WHITE);
		ImageIcon backbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/backbutton.jpg"));
		backbtn = new ImageIcon(backbtn.getImage().getScaledInstance(215, 100, Image.SCALE_SMOOTH));
		btnBack.setIcon(backbtn);
		
		ImageIcon backbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/backbuttonPressed.jpg"));
		backbtnPressed = new ImageIcon(backbtnPressed.getImage().getScaledInstance(215, 100, Image.SCALE_SMOOTH));
		btnBack.setPressedIcon(backbtnPressed);
		btnBack.setDisabledIcon(backbtnPressed);
		btnBack.setBackground(Color.WHITE);
		btnBack.setMargin(new Insets(0,0,0,0));
		
		// return to the LevelScreen
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Choose the level again?", "Return to menu",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				// if user clicked "YES"
				if (option == 0) {
					dispose();
					LevelScreen levelScreen = new LevelScreen();
					levelScreen.setVisible(true);
				}
					
			}
		});
		btnBack.setFont(new Font("Algerian", Font.PLAIN, 30));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBack.gridx = 3;
		gbc_btnBack.gridy = 2;
		timerPanel.add(btnBack, gbc_btnBack);
		btnReady.setFont(new Font("Algerian", Font.PLAIN, 30));
		GridBagConstraints gbc_btnReady = new GridBagConstraints();
		gbc_btnReady.insets = new Insets(0, 0, 0, 5);
		gbc_btnReady.fill = GridBagConstraints.BOTH;
		gbc_btnReady.gridx = 1;
		gbc_btnReady.gridy = 2;
		timerPanel.add(btnReady, gbc_btnReady);
		
		
	}
	
	// execute the countdown task when click the button
	public class CountDownTask implements Runnable{
		@Override
		public void run() {
			btnReady.setEnabled(false);
			btnBack.setEnabled(false);
			btnReady.setDisabledIcon(startbtnPressed);
			
			for(int i = 0; i <= 5; i++) {
				try {
					CountDown.sleep(1000);
					lblCountdown.setText(String.format("%d", 5-i));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			StageScreen stgFrame = new StageScreen();
			stgFrame.setVisible(true);
			btnReady.setEnabled(true);
			btnBack.setEnabled(true);
			dispose();
		}
	}

}
