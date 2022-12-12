import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class LevelScreen extends JFrame{

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JButton btnLevel1;
	private JButton btnLevel3;
	private JButton btnLevel5;
	private JButton btnLevel7;
	private JButton btnLevel2;
	private JButton btnLevel4;
	private JButton btnLevel6;
	private JButton btnLevel8;
	private JLabel lblStart;
	private JButton[] btnList;	// to manage a multiple buttons
	private int level;
	private int count;
	private JButton btnBack;
	GridBagConstraints gbc_btnLevel;


	/**
	 * Create the frame.
	 */
	public LevelScreen() {
		// resize the levelIcon and levelIconDisabled to fit into JButton
		ImageIcon levelIcon = new ImageIcon(StartApplication.class.getResource("/levelImage/levelCanClick.jpg"));
		levelIcon = new ImageIcon(levelIcon.getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH));
		ImageIcon levelIconDisabled = new ImageIcon(StartApplication.class.getResource("/levelImage/levelCannotClick.jpg"));
		levelIconDisabled = new ImageIcon(levelIconDisabled.getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH));
		
		gbc_btnLevel = new GridBagConstraints();
		
		btnList = new JButton[]{btnLevel1, btnLevel2, btnLevel3, btnLevel4,
								btnLevel5, btnLevel6, btnLevel7, btnLevel8};
		for(int i=0; i<8; i++)
			btnList[i] = new JButton();
		
		for(int i=1; i<8; i++) {	// initialized with disabled
			btnList[i].setEnabled(false);
		}
		
		count = 1;
		
		// initialize the file userChoose.txt
		try {
			new FileOutputStream("userChoose.txt").close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileInputStream fileObject = new FileInputStream("record.txt");
			Scanner reader = new Scanner(fileObject);
			while (reader.hasNext())
				count += Integer.parseInt(reader.nextLine());
			
			for(int i=0; i<count; i++) {
				btnList[i].setEnabled(true);
			}
		} catch (FileNotFoundException fe) {
		}
		
		setTitle("Fruit Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 1100, 740);  
        contentPane = new JPanel() {  
             public void paintComponent(Graphics g) {  // override method which makes a background image
                  Image img = Toolkit.getDefaultToolkit().getImage(  
                            MyFrame.class.getResource("/levelImage/levelBackground.jpg"));  
                  g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
             }  
        };  
        setContentPane(contentPane);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {200, 300, 100, 300, 170, 30};
		gbl_panel.rowHeights = new int[] {80, 80, 45, 80, 45, 80, 45, 80, 70, 15};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		contentPane.setLayout(gbl_panel);
		
		lblStart = new JLabel("** Let's Start!! **");
		lblStart.setBackground(Color.LIGHT_GRAY);
		lblStart.setFont(new Font("Algerian", Font.PLAIN, 60));
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.gridwidth = 3;
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 1;
		gbc_lblStart.gridy = 0;
		contentPane.add(lblStart, gbc_lblStart);
		
		for (int i=0; i<8; i++) {
			btnList[i].setText(String.format("Level %d", i+1));
			btnList[i].setForeground(Color.WHITE);
			btnList[i].setFont(new Font("Algerian", Font.PLAIN, 40));
			btnList[i].setBackground(Color.WHITE);
			btnList[i].setHorizontalTextPosition(JButton.CENTER);
			btnList[i].setMargin(new Insets(0,0,0,0));
			
			
			btnList[i].setIcon(levelIcon);
			btnList[i].setPressedIcon(levelIconDisabled);
			btnList[i].setDisabledIcon(levelIconDisabled);
			
			btnList[i].addActionListener(new LevelActionListener());
			
			gbc_btnLevel.insets = new Insets(0,0,0,0);
			gbc_btnLevel.fill = GridBagConstraints.BOTH;
			gbc_btnLevel.gridx = (2*i+1) % 4;
			gbc_btnLevel.gridy = 2*((2*i) / 4) + 1;
			contentPane.add(btnList[i], gbc_btnLevel);
		}
		
		ImageIcon backbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/backbutton.jpg"));
		backbtn = new ImageIcon(backbtn.getImage().getScaledInstance(170, 70, Image.SCALE_SMOOTH));
		ImageIcon backbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/backbuttonPressed.jpg"));
		backbtnPressed = new ImageIcon(backbtnPressed.getImage().getScaledInstance(170, 70, Image.SCALE_SMOOTH));
		
		btnBack = new JButton();
		// return to StartApplication
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Return to the Start Page?", "Return to Start",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				// if user clicked "YES"
				if (option == 0) {
					dispose();
					StartApplication startScreen = new StartApplication();
					startScreen.setVisible(true);
				}
			}
		});
		btnBack.setBackground(Color.WHITE);
		btnBack.setHorizontalTextPosition(JButton.CENTER);
		btnBack.setMargin(new Insets(0,0,0,0));
		
		btnBack.setIcon(backbtn);
		btnBack.setPressedIcon(backbtnPressed);
		
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0,0,0,0);
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.gridx = 4;
		gbc_btnBack.gridy = 8;
		contentPane.add(btnBack, gbc_btnBack);
	}
	
	public class LevelActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
				
			if (e.getSource() == btnList[0])	level = 1;
			else if (e.getSource() == btnList[1]) level = 2;
			else if (e.getSource() == btnList[2]) level = 3;
			else if (e.getSource() == btnList[3]) level = 4;
			else if (e.getSource() == btnList[4]) level = 5;
			else if (e.getSource() == btnList[5]) level = 6;
			else if (e.getSource() == btnList[6]) level = 7;
			else level = 8;
			
			// write the file userChoose.txt
			try {
				FileOutputStream fileStream = new FileOutputStream("userChoose.txt", true);
				PrintWriter writer = new PrintWriter(fileStream);
				writer.println(String.format("%d", level));
				writer.close();
				fileStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			DifficultyScreen diffiFrame = new DifficultyScreen();
			diffiFrame.setVisible(true);
			
			dispose();
		}
		
		
	}
	
	public int getLevel() {
		return level;
	}
}

