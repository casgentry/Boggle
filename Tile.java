import java.awt.*;
import java.awt.font.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Date: 14Oct12
 * Author: Cas Gentry
 **/

@SuppressWarnings("serial")
public class Tile extends JPanel{
	public char letter;
	public boolean visited;
	public boolean clicked;
	AttributedString print;
	
	public Tile(char l){
		letter = l;
		visited = false;
		clicked = false;
        print = new AttributedString(""+letter);
        print.addAttribute(TextAttribute.SIZE, 50);
		
        setBackground(Color.white);
		
        setBorder(new LineBorder(Color.black));
        setSize(new Dimension(100,100));
        setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paint(g);

		if(clicked) {g.setColor(Color.yellow); System.out.println("Repaint:"+letter);}
		else		g.setColor(Color.white);
		
		g.fillRect(1, 1, 98, 98);
		
		g.setColor(Color.black);
		g.drawString(print.getIterator(), 30, 60);
	}
	
	/*
	@Override
	public void mouseClicked(MouseEvent e) {
		clicked = board.buildWord(this);
		repaint();
	}
	*/
}
