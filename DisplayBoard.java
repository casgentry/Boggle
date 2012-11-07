import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
**/

@SuppressWarnings("serial")
public class DisplayBoard extends JFrame implements MouseListener{
	String userWord;
	LinkedList<Tile> word;
	Tile[][] board;
	
	public DisplayBoard(Tile[][] b, int length){
		super("Boggle");
		
		board = b;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(5, 5));
		setBackground(Color.gray);
		setSize(new Dimension(500, 500));
		addMouseListener(this);
		
		for(int i=0; i< length; i++){
			for(int j=0; j< length; j++){
				add(board[i][j]);
			}
		}
		
		setVisible(true);
	}
	
	public boolean buildWord(int r, int c){
		//if this is a new word...
		if(userWord == null){
			userWord = Character.toString(board[r][c].letter);
			word = new LinkedList<Tile>();
			word.add(board[r][c]);
		}
		//otherwise, check it's near the last letter
		else{
			if(locationCheck(r, c)){
				userWord = userWord.concat(Character.toString(board[r][c].letter));
				word.add(board[r][c]);
				System.out.println(userWord);
			}
			else return false;
		}
		return true;
	}
	
	public boolean locationCheck(int row, int col){
		//check that this letter is close to the last one
		return true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		int x = e.getX();//gets x coord when clicked; (note: x = column)
		int y = e.getY(); //gets y coord when clicked; (note: y = row)
		int row = y/100;
		int col = x/100;
		
		buildWord(row, col);
		System.out.println(userWord);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
