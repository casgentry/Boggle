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
	public boolean visited, clicked, hover;
	AttributedString print;
	int rowLoc, colLoc;
	
	public Tile(char l, int r, int c){
		letter = l;
		visited = false;
		clicked = false;
		rowLoc = r; colLoc = c;
        print = new AttributedString(""+letter);
        print.addAttribute(TextAttribute.SIZE, 50);
		
        setBackground(Color.white);
		
        setBorder(new LineBorder(Color.black));
        setSize(new Dimension(100,100));
        setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		if(hover) { g.setColor(Color.yellow); }
		if(clicked) {g.setColor(Color.yellow); }
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
