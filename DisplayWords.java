import java.awt.*;
import javax.swing.*;

/**
 * Date: 14Oct12
 * Author: Cas Gentry
 **/

@SuppressWarnings("serial")
public class DisplayWords extends JPanel{
	JTextArea userWords;
	
	public DisplayWords(){
		setBackground(Color.white);
		setLayout(new GridLayout(1,1));
		
		userWords = new JTextArea();
		userWords.setEditable(false);
		userWords.setBorder(BorderFactory.createLineBorder(Color.black));
		userWords.setBackground(Color.white);
		add(userWords);
		
		setVisible(true);
	}
}
