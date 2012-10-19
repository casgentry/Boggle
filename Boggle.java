import java.io.*;
import java.util.*;

/**
 * Date: 14Oct12
 * Author: Cas Gentry
 * Description: Boggle.java, main class for word search game
 * 
 * Given the following grid:
 * A B C D E
 * F G H I J
 * K L M N O
 * P R S T U
 * V W X Y Z
 * 
 * Write a program that prints a list of all the English words
 * that be found by starting at any letter and traversing to only
 * adjacent letters. Adjacent letters are defined as letters that
 * can be reached by going left, right, up, or down. For example:
 *   H I D E
 *   
 *   Questions: Can you hit a letter more than once? - Decided no
**/

public class Boggle{
	//Dictionary index - TrieNodes build words, this references each tree
	HashMap<Character, TrieNode> roots = new HashMap<Character, TrieNode>();

	Tile[][] grid;		//array to hold the board
	final int N = 5; 	//static length of the board
	LinkedList<String> foundWords; //LinkedList containing all words found on the board

	//call the boggle class
	public static void main(String[] args) { new Boggle(); }

	public Boggle() {
		//process the dictionary file
		readFile("words.txt");
		
		//initialize found words list, it's empty right now
		foundWords = new LinkedList<String>();
		
		//create a data structure to hold the board, an array of Tiles
		//Tiles know their letter and if they've been visited
		grid = new Tile[N][N];

		//read in the board
		readFile("board.txt");
		
		//display the board
		System.out.println("-------------");
		for(int a=0; a<N; a++){
			for(int b=0; b<N; b++){
				System.out.print(grid[a][b].letter+" ");
			}
			System.out.println();
		}
		System.out.println("-------------");
		
		//find all possible words in the boggle board
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				//send empty string & cycle through possible start points
				findWords("", i, j);
			}
		}
		
		//alphabetize the list of found words
		Collections.sort(foundWords);
		Iterator<String> it = foundWords.iterator();
		
		System.out.println("The following words can be found on this board:");
		
		int cols=0;
		while(it.hasNext()){			
			System.out.print(it.next()+"\t"); //iterate and print for the user
			cols++;
			if(cols>5){ System.out.println(); cols=0; } //display using columns
		}
	}
	
	/* Read in a file with FileReader and Scanner and process */
	public void readFile(String filename){
		FileReader fr = null;
		//Read in the file, if it fails, print an error message
        try { fr = new FileReader(filename); }
        catch(Exception e){ System.out.println("File load error: "+filename); }
        
        //create a scanner, get next line
        Scanner sc = new Scanner(fr);
        int row = 0;
        //process each line
        while(sc.hasNext()){
        	String line = sc.nextLine();
        	//tokenize the board and add words to the trie tree
        	 //increment the row as you process
        	if(filename.contains("board"))		{ row = processBoard(line, row); }
        	else if(filename.contains("words")) { insert(line); }
        }
	}
	
	/* tokenize the board, split columns into board matrix */
	public int processBoard(String line, int row){
    	try{
			StringTokenizer st = new StringTokenizer(line);
			int col = 0;
			while(st.hasMoreTokens()){
				grid[row][col] = new Tile(st.nextToken().charAt(0));
				//System.out.println("row: "+row+", col: "+col+", "+grid[row][col]);
				col++;
			}
    	}
    	catch(Exception e){ System.out.println("There might be a problem with input file format."); }

    	return ++row;
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
		
		//can't visit a cell more than once, return if it's already been visited
		if(grid[row][col].visited){
			//System.out.println(prefix+" was already visited"); 
			return; 
		}

		//we're about to check the word, tag the letter we're on as visited
		grid[row][col].visited = true;
		//System.out.println(grid[row][col].letter+" is visited.");
		
		//grab the root node, this corresponds to the first char in the string
		node = roots.get(prefix.charAt(0));
		
		//don't process for single letter words, articles not counted as words
		if(prefix.length() > 1){
			if(!existinTree(prefix, node)){ //if it doesn't exist in the tree
				grid[row][col].visited = false; //reset this node as unvisited
				//System.out.println(prefix+" is not in tree."); 
				//System.out.println(grid[row][col].letter+" is UNvisited.");
				return; //back up a node
			}
			
			//since it exists in the tree, check if it's a full word
			if(isFullWord(prefix, node)){
				if(!foundWords.contains(prefix)){
					foundWords.add(prefix); //if it is, add to the foundWords list
					//System.out.println(prefix);
				}
			}
		}
		
		//consider neighboring letters on the board but not diagonals
		for(int a=-1; a <= 1; a++){
			for(int b=-1; b <= 1; b++){
				int nrow = row+a, ncol = col+b;
				//don't consider increments that fall outside the board
				//throw out diagonals by comparing absolute value of increments
				if(nrow >= 0 && ncol >= 0 && nrow < N && ncol < N && Math.abs(a) != Math.abs(b)){
					findWords(prefix, nrow, ncol); //recursive search for words
				}
			}
		}

		grid[row][col].visited = false; //once this is complete, set this node back to unvisited
		//System.out.println(grid[row][col].letter+" is UNvisited.");
	}
}
