import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

	private TrieNode root;
	private List<String> roadNames;
	
	private Map<String, Road> prefixRoads;
	
	private List<Road> selectRoads;
	
    public Trie(Map<Integer, Road> roads) {
        
    	this.root = new TrieNode();
    	
    	this.roadNames = new ArrayList<String>();
    	this.prefixRoads = new HashMap<String, Road>();
    	
    	initTrie(roads);
    }
 
    public void initTrie(Map<Integer, Road> roads){
    	
    	for(Road r : roads.values()){
    		if( ! roadNames.contains(r.getLabel()))
    			roadNames.add(r.getLabel() +","+r.getCity());		//Get List of Unique Road Names + City
    		
    		prefixRoads.put(r.getLabel(), r);				//Initialize Data Struct with RoadNames as indexes to Roads
    	}
    	
    	
    	for(String s : roadNames)
    		insert(s);										//Insert all RoadNames into Trie
    	
    }

	// Inserts a word into the Trie.
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

	public TrieNode getRoot() {
		return root;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}

	public List<Road> getRoads(String prefix) {

		selectRoads = new ArrayList<Road>();
		
		for(int i = 0; i < prefix.length(); i++){
			
			for(Map.Entry<String, Road> entry : prefixRoads.entrySet()){
					
				if(i <= entry.getKey().length()-1){	
					
					String subStr = entry.getKey().substring(0, i+1);				//Get Substring of RoadName based off ith pos on prefix
			
					if(prefix.equals(subStr)){										//If Prefix Matches, Add to list of Roads
						selectRoads.add(entry.getValue());
					}
				}
			}
		}
		
		return selectRoads;
	}

	public Map<String, Road> getPrefixRoads() {
		return prefixRoads;
	}

	public String getRoadNames(String prefix) {

		StringBuilder sb = new StringBuilder();
		
		for(Road r : getRoads(prefix))
			sb.append("Street Name: " + r.getLabel() + "	City Name: " + r.getCity() + "\n");
		
		return sb.toString();
	}
    
    
	
}
