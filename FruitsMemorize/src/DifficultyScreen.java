import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Font;
import java.awt.Color;

public class DifficultyScreen extends JFrame{

	private JPanel contentPane;
	private JButton btnEasy;
	private JButton btnMedium;
	private JButton btnHard;
	
	public static int level, difficulty;	// class variables

	/**
	 * Create the frame.
	 */
	public DifficultyScreen() {
		setTitle("Difficulty");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 300, 600, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnEasy = new JButton("EASY");
		btnEasy.setForeground(Color.WHITE);
		btnEasy.setBackground(new Color(154, 205, 50));
		btnEasy.setFont(new Font("Algerian", Font.PLAIN, 40));
		contentPane.add(btnEasy);
		
		btnMedium = new JButton("MEDIUM");
		btnMedium.setForeground(Color.WHITE);
		btnMedium.setBackground(new Color(255, 165, 0));
		btnMedium.setFont(new Font("Algerian", Font.PLAIN, 40));
		contentPane.add(btnMedium);
		
		btnHard = new JButton("HARD");
		btnHard.setForeground(Color.WHITE);
		btnHard.setBackground(Color.RED);
		btnHard.setFont(new Font("Algerian", Font.PLAIN, 40));
		contentPane.add(btnHard);
		
		btnEasy.addActionListener(new DifficultyActionListener());
		btnMedium.addActionListener(new DifficultyActionListener());
		btnHard.addActionListener(new DifficultyActionListener());
	}
	
	public class DifficultyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnEasy) difficulty = 1;
			else if (e.getSource() == btnMedium) difficulty = 2;
			else difficulty = 3;
			
			// write the file userChoose.txt
			try {
				FileOutputStream fileStream = new FileOutputStream("userChoose.txt", true);
				PrintWriter writer = new PrintWriter(fileStream);
				writer.println(String.format("%d", difficulty));
				writer.close();
				fileStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			GetReadyScreen readyFrame = new GetReadyScreen();	
			
			readyFrame.setVisible(true);
			dispose();
		}
	}
	
	public int getDifficulty() {
		return difficulty;
	}

}
