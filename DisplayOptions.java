import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
**/

@SuppressWarnings("serial")
public class DisplayOptions extends JFrame implements ActionListener{
	Button addWord;
	JPanel userWords;
	Boggle home;
	
	public DisplayOptions(){
		super("Boggle");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.gray);
		setSize(new Dimension(100, 500));
		setLocation(505, 0);
		
		addWord = new Button("Add Word");
		addWord.addActionListener(this);
		add(addWord, BorderLayout.NORTH);
		
		userWords = new JPanel();
		userWords.setSize(new Dimension(100,350));
		userWords.setBackground(Color.white);
		add(userWords);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addWord){
			System.out.println("Add a word!");
			System.out.println(home.display.userWord);
		}
	}
}
