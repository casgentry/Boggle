import java.awt.*;
import javax.swing.*;

/**
 * Date: 14Oct12
 * Author: Cas Gentry
 **/
 
@SuppressWarnings("serial")
public class DisplayOptions extends JPanel{
	Button addWord;
	JTextField timer, score;
	Boggle home;
	
	public DisplayOptions(){
		setLayout(new GridLayout(1,3));
		setPreferredSize(new Dimension(235, 40));
		setBackground(Color.gray);
		
		addWord = new Button("Add Word");
		add(addWord);
		
		timer = new JTextField("0:00");
		timer.setEditable(false);
		timer.setAlignmentX(CENTER_ALIGNMENT);
		timer.setAlignmentY(CENTER_ALIGNMENT);
		timer.setFont(new Font("SansSerif", Font.BOLD, 40));
		timer.setBorder(BorderFactory.createLineBorder(Color.black));
		timer.setBackground(Color.white);
		add(timer);
		
		score = new JTextField("");
		score.setEditable(false);
		score.setAlignmentX(CENTER_ALIGNMENT);
		score.setAlignmentY(CENTER_ALIGNMENT);
		score.setFont(new Font("SansSerif", Font.BOLD, 40));
		score.setBorder(BorderFactory.createLineBorder(Color.black));
		score.setBackground(Color.LIGHT_GRAY);
		add(score);
		
		setVisible(true);
	}
}
