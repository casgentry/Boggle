package BoggleGame;

import java.util.*;

/**
 * Date: 14Oct12
 * Author: Cas Gentry
 **/

public class TrieNode{
	public boolean fullWord = false;
	public HashMap<Character,TrieNode> children;
	public char letter;
	
	public TrieNode(char l){
		this.letter = l;
		children = new HashMap<Character,TrieNode>();
		this.fullWord = false;
	}
}
