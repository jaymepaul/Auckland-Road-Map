import java.util.HashMap;

public class TrieNode {

	char c;								
	boolean isRoad;						//A flag to determine if it is a valid entry
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();		//Children of Node
    
 
    public TrieNode() {}
 
    /**TrieNode constructor, simply takes in a character and sets it
     * 
     * @param char c*/
    public TrieNode(char c){
        this.c = c;
    }
}
