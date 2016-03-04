import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

	private TrieNode root;
	private Map<Integer, Road> roads;
	 
    public Trie(Map<Integer, Road> roads) {
        
    	this.root = new TrieNode();
    	this.roads = roads;
        initTrie();
    }
 
    private void initTrie() {
		
    	List<String> roadNames = new ArrayList<String>();
    	
		for(Road r : roads.values()){
			if(!roadNames.contains(r.getLabel()))
				roadNames.add(r.getLabel());					//Build list of unique names
		}
		
		for(String s : roadNames)
			insert(s);											//Add each roadName to Trie
		
	}

	// Inserts a word into the trie.
    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;
 
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
 
            TrieNode t;
            if(children.containsKey(c)){
                    t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }
 
            children = t.children;
 
            //set leaf node
            if(i==word.length()-1)
                t.isRoad = true;    
        }
    }
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);
 
        if(t != null && t.isRoad) 
            return true;
        else
            return false;
    }
 
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null) 
            return false;
        else
            return true;
    }
 
    public TrieNode searchNode(String str){
        Map<Character, TrieNode> children = root.children; 
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }
 
        return t;
    }
	
}
