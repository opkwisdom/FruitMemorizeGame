import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;

public class StartApplication extends JFrame {

	private JPanel contentPane;
	private JPanel FruitsPanel1;
	private JPanel FruitsPanel2;
	private JPanel ButtonPanel;
	private JLabel lblBlueberry;
	private JLabel lblBanana;
	private JLabel lblApple;
	private JLabel lblLemon;
	private JLabel lblCherry;
	private JLabel lblGame;
	private JPanel btnPanel;
	private JButton btnStart;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartApplication frame = new StartApplication();
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
	public StartApplication() {
//		// initialize the file userChoose.txt
//		try {
//			new FileOutputStream("userChoose.txt").close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		try {
			FileOutputStream fileStream = new FileOutputStream("record.txt", true);
			PrintWriter writer = new PrintWriter(fileStream);
			writer.print("");
			writer.close();
			fileStream.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		setTitle("Fruit Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 1100, 740);	// 1100 * 740 window size
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		// panel for the fruit image
		FruitsPanel1 = new JPanel();
		FruitsPanel1.setBackground(Color.WHITE);
		contentPane.add(FruitsPanel1);
		FruitsPanel1.setLayout(new GridLayout(0, 3, 0, 0));
		
		lblApple = new FruitImageJLabel("Apple",350,230);
		FruitsPanel1.add(lblApple);
		
		lblBanana = new FruitImageJLabel("Banana",350,230);
		FruitsPanel1.add(lblBanana);
		
		lblBlueberry = new FruitImageJLabel("Blueberry",350,230);
		FruitsPanel1.add(lblBlueberry);
		
		// panel for the fruit image
		FruitsPanel2 = new JPanel();
		FruitsPanel2.setBackground(Color.WHITE);
		contentPane.add(FruitsPanel2);
		
		lblLemon = new FruitImageJLabel("Lemon",350,230);
		FruitsPanel2.add(lblLemon);
		
		lblCherry = new FruitImageJLabel("Cherry",350,230);
		FruitsPanel2.add(lblCherry);
		
		ButtonPanel = new JPanel();
		ButtonPanel.setBackground(Color.WHITE);
		contentPane.add(ButtonPanel);
		ButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblGame = new JLabel("* Fruits Game *");
		lblGame.setBackground(Color.WHITE);
		lblGame.setFont(new Font("Algerian", Font.PLAIN, 70));
		lblGame.setHorizontalAlignment(SwingConstants.CENTER);
		ButtonPanel.add(lblGame);
		
		btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		ButtonPanel.add(btnPanel);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {215, 215, 215, 215, 215};
		gbl_btnPanel.rowHeights = new int[]{115, 0};
		gbl_btnPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_btnPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		btnPanel.setLayout(gbl_btnPanel);
		
		btnStart = new JButton("");
		ImageIcon startbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/startbutton.jpg"));
		startbtn = new ImageIcon(startbtn.getImage().getScaledInstance(215, 115, Image.SCALE_SMOOTH));
		btnStart.setIcon(startbtn);
		
		ImageIcon startbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/startbuttonPressed.jpg"));
		startbtnPressed = new ImageIcon(startbtnPressed.getImage().getScaledInstance(215, 115, Image.SCALE_SMOOTH));
		btnStart.setPressedIcon(startbtnPressed);
		btnStart.setMargin(new Insets(0,0,0,0));
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LevelScreen levelList = new LevelScreen();
				levelList.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				levelList.setVisible(true);
				dispose();
			}
		});
		btnStart.setFont(new Font("Algerian", Font.PLAIN, 50));
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 0, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 0;
		btnPanel.add(btnStart, gbc_btnStart);
		
		btnExit = new JButton("");
		ImageIcon exitbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/exitbutton.jpg"));
		exitbtn = new ImageIcon(exitbtn.getImage().getScaledInstance(215, 115, Image.SCALE_SMOOTH));
		btnExit.setIcon(exitbtn);
		
		ImageIcon exitbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/exitbuttonPressed.jpg"));
		exitbtnPressed = new ImageIcon(exitbtnPressed.getImage().getScaledInstance(215, 115, Image.SCALE_SMOOTH));
		btnExit.setPressedIcon(exitbtnPressed);
		btnExit.setMargin(new Insets(0,0,0,0));
		
		// if user clicked "Yes", program has downed
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?", "Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				// if user clicked "YES"
				if (option == 0)
					dispose();
			}
		});
		btnExit.setFont(new Font("Algerian", Font.PLAIN, 50));
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.gridx = 3;
		gbc_btnExit.gridy = 0;
		btnPanel.add(btnExit, gbc_btnExit);
	}

}
