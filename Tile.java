/**
 * Date: 14Oct12
 * Author: Cas Gentry
 * Description: Tile.java, Tile object for letters on the board
 *              knows it's associated letter and if it's been visited
 *              useful in preventing duplicate letter use during recursion
 **/

public class Tile {
	public char letter;
	public boolean visited;
	
	public Tile(char l){
		letter = l; //gets from main class
		visited = false; //initialized to false and set in main
	}
}
