import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// setIcon with fruit image depends on the input 
	public class FruitImageJLabel extends JLabel{
		
		public FruitImageJLabel(String fruit, int width, int height){
			ImageIcon imageIcon;
			
			if (fruit != null) {
				if (fruit == "Apple") {
					imageIcon = new ImageIcon(StartApplication.class.getResource("/fruits/apple.jpg"));
				} else if (fruit == "Banana") {
					imageIcon = new ImageIcon(StartApplication.class.getResource("/fruits/banana.jpg"));
				} else if (fruit == "Blueberry") {
					imageIcon = new ImageIcon(StartApplication.class.getResource("/fruits/blueberry.jpg"));
				} else if (fruit == "Lemon") {
					imageIcon = new ImageIcon(StartApplication.class.getResource("/fruits/lemon.jpg"));
				} else {
					imageIcon = new ImageIcon(StartApplication.class.getResource("/fruits/cherry.jpg"));
				}
				
				imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
				this.setHorizontalAlignment(FruitImageJLabel.CENTER);
				this.setIcon(imageIcon);
			}	
		}
	}
