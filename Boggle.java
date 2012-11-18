import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Date: 14Oct12
 * Last update: 06Nov12
 * Author: Cas Gentry
 * 
 * Generate a random board.
 * 
 * Determine all the English words found by starting at any letter and 
 * traversing to only adjacent letters. Adjacent letters are defined as
 * letters that can be reached by going left, right, up, or down.
 * 
 * Let user pick words & check against list.
**/

@SuppressWarnings("serial")
public class Boggle extends JFrame implements ActionListener, KeyListener{
	//holds the dictionary index
	HashMap<Character, TrieNode> roots = new HashMap<Character, TrieNode>();
	//array to hold the board
	Tile[][] grid;
	DisplayBoard display;
	DisplayOptions menu;
	DisplayWords list;
	CountDown timer;
	Button addWord;
	final int N = 5; //static length of the board
	LinkedList<String> foundWords;
	LinkedList<String> userWords;
	int score;

	public static void main(String[] args) { new Boggle(); }

	public Boggle(){
		super("Boggle");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setBackground(Color.gray);
		setSize(new Dimension(600, 600));
		setResizable(false);
		
		//process the dictionary
		readFile("words.txt");
		foundWords = new LinkedList<String>();

		//create a board
		grid = randomBoard(N);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		//create the boggle display
		display = new DisplayBoard(grid, N);
		c.gridheight = 5;
		c.gridwidth = 5;
		c.weightx = 5.0;
		c.weighty = 5.0;
		c.ipadx = 300;
		c.ipady = 300;
		
		add(display, c);

		c.ipadx = 0;
		c.ipady = 0;
		
 		menu = new DisplayOptions();
		menu.addWord.addActionListener(this);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 5;
		
		add(menu, c);
		
		list = new DisplayWords();
		list.setFocusable(true);
		list.addKeyListener(this);
		c.gridx = 5;
		c.gridy = 0;
		c.ipadx = 0;
		c.gridheight = 6;
		
		add(list, c);

		timer = new CountDown(menu, this);
		
		setVisible(true);
		
		//find all possible words in the boggle board
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				//send empty string & cycle through possible start points
				findWords("", i, j);
			}
		}
		
		Collections.sort(foundWords);
		/*
		Iterator<String> it = foundWords.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		*/
		userWords = new LinkedList<String>();
	}
	
	//generate a random playing board
	public Tile[][] randomBoard(int dimension){
		Tile[][] board = new Tile[dimension][dimension];
		Random generator = new Random();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int alpha = 26, boardl = dimension * dimension;
		int row = 0, col = 0;
		while(boardl > 0){
			int r = generator.nextInt(alpha);
			char a = alphabet.charAt(r);
			board[row][col] = new Tile(a, row, col);
			alphabet = alphabet.replace(Character.toString(a), "");
			col++;
			if(col>=dimension){ col = 0; row++; }
			alpha--; boardl--;
		}
		
		return board;
	}
	
	/* Read in a file with FileReader and Scanner and process */
	public void readFile(String filename){
		FileReader fr = null;
		//Read in the file, if it fails, print an error message
        try { fr = new FileReader(filename); }
        catch(Exception e){ System.out.println("File load error: "+filename); }
        
        //create a scanner, get next line
        Scanner sc = new Scanner(fr);
        //process each line
        while(sc.hasNext()){
        	String line = sc.nextLine();
        	//add words to the trie tree 
        	insert(line);
        }
	}
	
	/* insert a word into the dictionary */
	public void insert(String word){
		//if the root node does not contain the first letter in the index
		if(!roots.containsKey(word.charAt(0))){
			//put that letter in the index and create a new associated TrieNode
			roots.put(word.charAt(0), new TrieNode(word.charAt(0)));
		}
		
		//send the rest of the word for processing and the TrieNode of the first letter
		insertWord(word.substring(1), roots.get(word.charAt(0)));
	}
	
	/* recursive method that inserts new word into Trie tree */
	public void insertWord(String word, TrieNode node){
		final TrieNode nextChild;
		//each node has children nodes to build different words
		//check if the children nodes contain the next letter in the word
		if(node.children.containsKey(word.charAt(0))){
			//if it does, get that node
			nextChild = node.children.get(word.charAt(0));
		}
		//if it doesn't, create a new child node
		else{
			//send the new child node the next letter in the word
			nextChild = new TrieNode(word.charAt(0));
			//add the new node to the children list of the current node
			node.children.put(word.charAt(0), nextChild);
		}
		
		//if the length of the word is down to 1
		if(word.length() == 1){
			//note on that node that the word is complete
			nextChild.fullWord = true;
			return;
		}
		else{
			//recurse with the rest of the word and send the new child node
			insertWord(word.substring(1), nextChild);
		}
	}
	
	/* Recursive method to search through a Trie tree to find the value */
	public boolean isFullWord(String word, TrieNode node){
		//if the length of the word is 0
		if(word.length() == 1){
			//check if it's registered as a complete word
			return node.fullWord;
		}
	
		//check the children nodes for the next character in the word
		if(node.children.containsKey(word.charAt(1))){
			TrieNode send = node.children.get(word.charAt(1));
			//if it exists, recurse and return if it's a word
			return isFullWord(word.substring(1), send);
		}
		//if the next character doesn't exist in the children nodes, it's not a word
		else{ return false; }
	}
	
	public boolean existinTree(String prefix, TrieNode node){
		boolean ret = true;
		if(prefix.length() > 1){
			//check the children nodes for the next character in the word
			if(!node.children.containsKey(prefix.charAt(1))){ return false; }
			else{ ret = existinTree(prefix.substring(1), node.children.get(prefix.charAt(1))); }
		}
		
		return ret;
	}
	
	/* depth first search starting with cell (i, j) */
	public void findWords(String prefix, int row, int col){
		prefix = prefix.concat(Character.toString(Character.toLowerCase(grid[row][col].letter)));
		//System.out.println("findWords: "+prefix);
		
		TrieNode node;
		//if it falls outside the bounds of the board, return
		if (row < 0 || col < 0 || row >= N || col >= N){ 
			//System.out.println("Out of bounds."); 
			return; 
		}
		
		//can't visit a cell more than once, note if it's already been visited
		if(grid[row][col].visited){
			//System.out.println(prefix+" was already visited"); 
			return; 
		}

		grid[row][col].visited = true;
		//System.out.println(grid[row][col].letter+" is visited.");
		
		//grab the root node in the string by the first char in the string
		node = roots.get(prefix.charAt(0));
		
		//don't process for single letter words, articles not counted as words
		if(prefix.length() > 2){
			if(!existinTree(prefix, node)){ 
				grid[row][col].visited = false;
				//System.out.println(prefix+" is not in tree."); 
				//System.out.println(grid[row][col].letter+" is UNvisited.");
				return; 
			}
			
			if(isFullWord(prefix, node)){
				if(!foundWords.contains(prefix)){
					foundWords.add(prefix);
					//System.out.println(prefix);
				}
			}
		}
		
		//consider neighbors, not diagonals though
		for(int a=-1; a <= 1; a++){
			for(int b=-1; b <= 1; b++){
				int nrow = row+a, ncol = col+b;
				if(nrow >= 0 && ncol >= 0 && nrow < N && ncol < N && Math.abs(a) != Math.abs(b)){
					findWords(prefix, nrow, ncol);
				}
			}
		}

		grid[row][col].visited = false;
		//System.out.println(grid[row][col].letter+" is UNvisited.");
	}
	
	public void updateMenu(){
		list.userWords.append(userWords.getLast()+"\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==menu.addWord && display.play){
			addWord();
		}
	}
	
	public void addWord(){
		//all the words have been found
		if(userWords.size() == foundWords.size()){
			//stop the game
			timer.stopTimer();
			display.clearLetters();
			tallyPoints();
		}
		else{
			if(foundWords.contains(display.userWord.toLowerCase())){
				//add the word to the list, set to null
				if(!userWords.contains(display.userWord.toLowerCase())){
					userWords.addLast(display.userWord.toLowerCase()+"");
					updateMenu();
					list.repaint();
				}
				else{
					JOptionPane.showMessageDialog(display, display.userWord+" was already found.");
				}
			}
			else{
				JOptionPane.showMessageDialog(display, display.userWord+" is not a word.");
			}
	
			display.userWord = null;
			display.click = false;
			display.clearLetters();
			
			for(int i=0; i<N; i++){
				for(int j=0; j<N; j++){
					grid[i][j].repaint();
				}
			}
		}
		
		//all the words have been found
		if(userWords.size() == foundWords.size()){
			//stop the game
			timer.stopTimer();
			display.clearLetters();
			tallyPoints();
		}
	}

	@Override
	public void keyPressed(KeyEvent k) { 
		char eventChar = k.getKeyChar();
		for(int i=0; i<N; i++){
			for(int j=0; j<5; j++){
				if(grid[i][j].letter == Character.toUpperCase(eventChar)){
					display.buildWord(i, j);
				}
			}
		}
		
		int enter = k.getKeyCode();
		if(enter == KeyEvent.VK_ENTER){
			addWord();
		}
	}

	@Override
	public void keyReleased(KeyEvent k) { }

	@Override
	public void keyTyped(KeyEvent k) { }
	
	public void tallyPoints(){
		//score userWords
		score = 0;
		
		Iterator<String> it = userWords.iterator();
		while(it.hasNext()){
			int wordL = it.next().length();
			if(wordL < 4) score++;
			else if(wordL < 5) score = score + 2; 
			else if(wordL < 6) score = score + 3;
			else if(wordL < 7) score = score + 5;
			else if(wordL > 7) score = score + 11;
		}
		
		//System.out.println(score);
		JOptionPane.showMessageDialog(display, "Your score is "+score+".");
	}

}
