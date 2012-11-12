package BoggleGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
**/

@SuppressWarnings("serial")
public class DisplayBoard extends JPanel implements MouseListener{
	String userWord;
	LinkedList<Tile> word;
	Tile[][] board;
	int boardL;
	boolean click;
	boolean play;
	
	public DisplayBoard(Tile[][] b, int length){
		board = b;
		boardL = length;
		click = false;
		play = false;
		
		setLayout(new GridLayout(5, 5));
		addMouseListener(this);
		
		for(int i=0; i< boardL; i++){
			for(int j=0; j< boardL; j++){
				add(board[i][j]);
			}
		}
		
		setVisible(true);
	}
	
	public boolean buildWord(int r, int c){
		//if this is a new word...
		if(userWord == null){
			board[r][c].clicked = true;
			userWord = Character.toString(board[r][c].letter);
			word = new LinkedList<Tile>();
			word.add(board[r][c]);
		}
		//otherwise, check it's near the last letter
		else{
			if(locationCheck(r, c)){
				board[r][c].clicked = true;
				userWord = userWord.concat(Character.toString(board[r][c].letter));
				word.add(board[r][c]);
			}
			else return false;
		}
		
		repaint();
		return true;
	}
	
	public boolean locationCheck(int row, int col){
		boolean ret = false;
		//check that this letter is close to the last one
		//get the last letter location
		int rowDist = Math.abs(word.getLast().rowLoc - row);
		int colDist = Math.abs(word.getLast().colLoc - col);
		if(rowDist <= 1 && colDist <= 1 && rowDist != colDist){ ret = true; }
		
		return ret;
	}
	
	public void clearLetters(){
		for(int i=0; i< boardL; i++){
			for(int j=0; j< boardL; j++){
				board[i][j].clicked = false;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e){ click = true; mouseAction(e); }
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public void mouseAction(MouseEvent e){
		if(click && play){
			int coords[] = getCoords(e);
			buildWord(coords[0], coords[1]);
		}
	}
	
	public int[] getCoords(MouseEvent e){
		int x = e.getX();//gets x coord when clicked; (note: x = column)
		int y = e.getY(); //gets y coord when clicked; (note: y = row)
		int row = y/100;
		int col = x/100;
		
		int c[] = { row, col };
		return c;
	}
}
