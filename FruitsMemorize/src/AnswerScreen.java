import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.GridLayout;

public class AnswerScreen extends JFrame {

	private JPanel contentPane;
	private int level, difficulty;
	private JPanel btnPanel;
	private JPanel fruitPanel;
	private JPanel answerPanel;
	private JLabel lblAnswer;
	private JButton btnSend;
	private JButton btnDelete;
	private JButton btnReset;
	private JLabel lblApple;
	private JLabel lblBanana;
	private JLabel lblBlueberry;
	private JLabel lblCherry;
	private JLabel lblLemon;
	private JButton btnApple;
	private JButton btnBanana;
	private JButton btnBlueberry;
	private JButton btnCherry;
	private JButton btnLemon;
	private int row, col;
	private int length;
	private String userChoice;
	private FruitImageJLabel fruitImage;
	private GridBagConstraints gbc_userChoice;
	
	private ArrayList<String> answer;	// true answer list
	private ArrayList<String> userAnswer;	// compare userAnswer with answer
	private ArrayList<FruitImageJLabel> userAnswerImage;	// display the fruit image depends on userAnswer
	private JLabel lblLevel;

	/**
	 * Create the frame.
	 */
	public AnswerScreen() {
		
		// get level and difficulty from the file "userChoose.txt"
		try {
			FileInputStream fileObject1 = new FileInputStream("userChoose.txt");
			Scanner reader = new Scanner(fileObject1);
			
			if (reader.hasNext())
				level = Integer.parseInt(reader.nextLine());
			if (reader.hasNext())
				difficulty = Integer.parseInt(reader.nextLine());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		answer = new ArrayList<String>();
		// get answer from the file "fruitsList.txt" and save to answer
		try {
			FileInputStream fileObject2 = new FileInputStream("fruitsList.txt");
			Scanner reader = new Scanner(fileObject2);
			
			while (reader.hasNext()) {
				answer.add(reader.nextLine());
			}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// get the number of elements in answer
		length = answer.size();
		
		// to let the user inform of the answer rules
		JOptionPane.showMessageDialog(null, "Before get started, there are something that you should know\n"
				+"1. You should the memorize the sequence and list it\n"
				+"2. You can make your answer by clicking the fruit image button on your right side\n"
				+"3. The answer is filled from top left to bottom right",
				"Information message", JOptionPane.INFORMATION_MESSAGE);
		
		
		setTitle("Fruit Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 1100, 740);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] {215, 215, 215, 215, 215};
		gbl_btnPanel.rowHeights = new int[] {80, 80};
		gbl_btnPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_btnPanel.rowWeights = new double[]{0.0, 0.0};
		btnPanel.setLayout(gbl_btnPanel);
		
		lblAnswer = new JLabel("Make your answer by clicking right button!");
		lblAnswer.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 45));
		GridBagConstraints gbc_lblAnswer = new GridBagConstraints();
		gbc_lblAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_lblAnswer.gridwidth = 5;
		gbc_lblAnswer.gridx = 0;
		gbc_lblAnswer.gridy = 0;
		btnPanel.add(lblAnswer, gbc_lblAnswer);
		
		btnSend = new JButton("");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (answer.size() != userAnswer.size())
						throw new IOException();
					
					int option = JOptionPane.showConfirmDialog(null, "Are you ready to submit the answer?", "Submit",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (option == 0) {
						if (answer.equals(userAnswer)) {	// When user get the right answer
							JOptionPane.showMessageDialog(null, String.format("Congratulations! You cleared level %d", level), 
									"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
							// record the clear level
							try {
								int count = 0;
								
								FileInputStream fileObject = new FileInputStream("record.txt");
								Scanner reader = new Scanner(fileObject);
								while(reader.hasNext())
									count += Integer.parseInt(reader.nextLine());
								
								if (level > count) {
									FileOutputStream fileStream = new FileOutputStream("record.txt", true);
									PrintWriter writer = new PrintWriter(fileStream);
									writer.println("1");
									writer.close();
									fileStream.close();
								}
							} catch (FileNotFoundException fe) {
								fe.printStackTrace();
							}
						}
						else {
							JOptionPane.showMessageDialog(null, String.format("That's too bad... You failed to clear level %d...", level), 
									"Fail...", JOptionPane.ERROR_MESSAGE);
						}
						option = JOptionPane.showConfirmDialog(null, String.format("Do you want to retry level %d?", level), "Retry",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (option == 0) {	// return to GetReadyScreen
							GetReadyScreen readyFrame = new GetReadyScreen();
							readyFrame.setVisible(true);
						}
						else {	// return to levelList
							LevelScreen levelFrame = new LevelScreen();
							levelFrame.setVisible(true);
						}
						dispose();
					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, String.format("The size of your answer not equals to the size of the true answer\n"
							+ "Your answer: %d, True Answer: %d", userAnswer.size(), answer.size()), 
							"Size Error", JOptionPane.ERROR_MESSAGE);
				}
			}});
		ImageIcon sendbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/sendbutton.jpg"));
		sendbtn = new ImageIcon(sendbtn.getImage().getScaledInstance(215, 80, Image.SCALE_SMOOTH));
		btnSend.setIcon(sendbtn);
		
		ImageIcon sendbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/sendbuttonPressed.jpg"));
		sendbtnPressed = new ImageIcon(sendbtnPressed.getImage().getScaledInstance(215, 80, Image.SCALE_SMOOTH));
		btnSend.setPressedIcon(sendbtnPressed);
		btnSend.setMargin(new Insets(0,0,0,0));
		
		btnDelete = new JButton("");
		// delete the last added fruit image and userAnswer
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = userAnswer.size();
					if (pos <= 0)
						throw new IndexOutOfBoundsException();
					userAnswerImage.remove(pos-1);
					userAnswer.remove(pos-1);
					answerPanel.remove(pos-1);
					answerPanel.revalidate();
					answerPanel.repaint();
				} catch(IndexOutOfBoundsException ee) {
					JOptionPane.showMessageDialog(null, "There is no answer to delete", 
							"Answer Delete Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		ImageIcon deletebtn = new ImageIcon(StartApplication.class.getResource("/btnImage/deletebutton.jpg"));
		deletebtn = new ImageIcon(deletebtn.getImage().getScaledInstance(215, 80, Image.SCALE_SMOOTH));
		btnDelete.setIcon(deletebtn);
		
		ImageIcon deletebtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/deletebuttonPressed.jpg"));
		deletebtnPressed = new ImageIcon(deletebtnPressed.getImage().getScaledInstance(215, 80, Image.SCALE_SMOOTH));
		
		lblLevel = new JLabel(String.format("Level %d", level));
		lblLevel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 40));
		GridBagConstraints gbc_lblLevel = new GridBagConstraints();
		gbc_lblLevel.insets = new Insets(0, 0, 0, 5);
		gbc_lblLevel.gridx = 0;
		gbc_lblLevel.gridy = 1;
		btnPanel.add(lblLevel, gbc_lblLevel);
		btnDelete.setPressedIcon(deletebtnPressed);
		btnDelete.setMargin(new Insets(0,0,0,0));
		
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 1;
		btnPanel.add(btnDelete, gbc_btnDelete);
		btnSend.setPressedIcon(sendbtnPressed);
		btnSend.setMargin(new Insets(0,0,0,0));
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 1;
		btnPanel.add(btnSend, gbc_btnSend);
		
		btnReset = new JButton("");
		// clear the answerPanel and userAnswer
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = userAnswer.size();
					if (pos <= 0)
						throw new IndexOutOfBoundsException();
					
					int option = JOptionPane.showConfirmDialog(null, "Do you want to clear your answer?", "Clear",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (option == 0) {
						userAnswerImage.clear();
						userAnswer.clear();
						answerPanel.removeAll();
						answerPanel.revalidate();
						answerPanel.repaint();
					}
				} catch(IndexOutOfBoundsException ee2) {
					JOptionPane.showMessageDialog(null, "There is no answer to delete", 
							"Answer Reset Error", JOptionPane.ERROR_MESSAGE);;
				}
			}
		});
		ImageIcon resetbtn = new ImageIcon(StartApplication.class.getResource("/btnImage/resetbutton.jpg"));
		resetbtn = new ImageIcon(resetbtn.getImage().getScaledInstance(215, 80, Image.SCALE_SMOOTH));
		btnReset.setIcon(resetbtn);
		
		ImageIcon resetbtnPressed = new ImageIcon(StartApplication.class.getResource("/btnImage/resetbuttonPressed.jpg"));
		resetbtnPressed = new ImageIcon(resetbtnPressed.getImage().getScaledInstance(215, 80, Image.SCALE_SMOOTH));
		btnReset.setPressedIcon(resetbtnPressed);
		btnReset.setMargin(new Insets(0,0,0,0));
		
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.BOTH;
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 3;
		gbc_btnReset.gridy = 1;
		btnPanel.add(btnReset, gbc_btnReset);
		
		fruitPanel = new JPanel();
		fruitPanel.setBackground(Color.WHITE);
		contentPane.add(fruitPanel, BorderLayout.EAST);
		fruitPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		btnApple = new JButton("");
		btnApple.setBackground(Color.WHITE);
		ImageIcon applebtn = new ImageIcon(StartApplication.class.getResource("/fruits/apple.jpg"));
		applebtn = new ImageIcon(applebtn.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		btnApple.setIcon(applebtn);
		btnApple.setMargin(new Insets(0,0,0,0));
		fruitPanel.add(btnApple);
		
		btnBanana = new JButton("");
		btnBanana.setBackground(Color.WHITE);
		ImageIcon bananabtn = new ImageIcon(StartApplication.class.getResource("/fruits/banana.jpg"));
		bananabtn = new ImageIcon(bananabtn.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		btnBanana.setIcon(bananabtn);
		btnBanana.setMargin(new Insets(0,0,0,0));
		fruitPanel.add(btnBanana);
		
		btnBlueberry = new JButton("");
		btnBlueberry.setBackground(Color.WHITE);
		ImageIcon blueberrybtn = new ImageIcon(StartApplication.class.getResource("/fruits/blueberry.jpg"));
		blueberrybtn = new ImageIcon(blueberrybtn.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		btnBlueberry.setIcon(blueberrybtn);
		btnBlueberry.setMargin(new Insets(0,0,0,0));
		fruitPanel.add(btnBlueberry);
		
		btnCherry = new JButton("");
		btnCherry.setBackground(Color.WHITE);
		ImageIcon cherrybtn = new ImageIcon(StartApplication.class.getResource("/fruits/cherry.jpg"));
		cherrybtn = new ImageIcon(cherrybtn.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		btnCherry.setIcon(cherrybtn);
		btnCherry.setMargin(new Insets(0,0,0,0));
		fruitPanel.add(btnCherry);
		
		btnLemon = new JButton("");
		btnLemon.setBackground(Color.WHITE);
		ImageIcon lemonbtn = new ImageIcon(StartApplication.class.getResource("/fruits/lemon.jpg"));
		lemonbtn = new ImageIcon(lemonbtn.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		btnLemon.setIcon(lemonbtn);
		btnLemon.setMargin(new Insets(0,0,0,0));
		fruitPanel.add(btnLemon);
		
		userAnswer =  new ArrayList<String>();
		userAnswerImage = new ArrayList<FruitImageJLabel>();
		
		// set the row and col to display fruitsList in matrix format
		row = 2*(level/3)+1; col = 11;
		// answer panel
		answerPanel = new JPanel() {  
            public void paintComponent(Graphics g) {  // override method which makes a background image
                Image img = Toolkit.getDefaultToolkit().getImage(  
                          MyFrame.class.getResource("/levelImage/EnableFrame.jpg"));  
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
           }
        };
		answerPanel.setBackground(Color.WHITE);
		
		int[] heights = new int[row];
		int[] widths = new int[col];
		
		for(int i=0; i<row; i++)
			heights[i] = 80;
		for(int i=0; i<col; i++)
			widths[i] = 80;
		
		GridBagLayout gbl_answerPanel = new GridBagLayout();
		gbl_answerPanel.columnWidths = widths;
		gbl_answerPanel.rowHeights = heights;
		gbc_userChoice = new GridBagConstraints();
		
		answerPanel.setLayout(gbl_answerPanel);
		contentPane.add(answerPanel, BorderLayout.CENTER);
		
		btnApple.addActionListener(new fruitsClickedListener());
		btnBanana.addActionListener(new fruitsClickedListener());
		btnBlueberry.addActionListener(new fruitsClickedListener());
		btnCherry.addActionListener(new fruitsClickedListener());
		btnLemon.addActionListener(new fruitsClickedListener());
	}

	// add the fruit image to answerPanel depends on the fruit button
	public class fruitsClickedListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (userAnswer.size() >= length)
					throw new IndexOutOfBoundsException();
				
				userChoice = new String();
				if (e.getSource() == btnApple)	userChoice = "Apple";
				else if (e.getSource() == btnBanana) userChoice = "Banana";
				else if (e.getSource() == btnBlueberry) userChoice = "Blueberry";
				else if (e.getSource() == btnCherry) userChoice = "Cherry";
				else userChoice = "Lemon";
				
				int pos = userAnswer.size();
				
				GridBagConstraints gbc_userChoice = new GridBagConstraints();
				gbc_userChoice.fill = GridBagConstraints.BOTH;
				gbc_userChoice.insets = new Insets(0, 0, 0, 5);
				gbc_userChoice.gridx = (2*(pos % 5)+1) % 11;
				gbc_userChoice.gridy = 2*(pos / 5);
				
				userAnswer.add(userChoice);
				fruitImage = new FruitImageJLabel(userChoice, 80, 80);
				userAnswerImage.add(fruitImage);
				
				answerPanel.add(fruitImage, gbc_userChoice);
				answerPanel.revalidate();	// display the added image immediately 
			} catch (IndexOutOfBoundsException exp){
				JOptionPane.showMessageDialog(null, String.format("Your answer cannot exceed %d elements", length), 
						"Answer Error", JOptionPane.ERROR_MESSAGE);;
			}
			
			
			
		}
		
	}
}
