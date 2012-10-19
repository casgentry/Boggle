import java.util.*;

/**
 * Date: 14Oct12
 * Author: Cas Gentry
 * Description: TrieNode.java, Used in creating word trees for the dictionary
 *              Knows if it is a full word, what other letters fall below it in
 *              the tree, and what letter is associated with it 
 **/

public class TrieNode{
	public boolean fullWord = false;
	public HashMap<Character,TrieNode> children;
	public char letter;
	
	public TrieNode(char l){
		this.letter = l;  //get letter from main class
		//initialize children index, gets this from main class
		children = new HashMap<Character,TrieNode>();
		this.fullWord = false; //initialize to false
	}
}
