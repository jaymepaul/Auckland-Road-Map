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
 
    /**Initializes the Trie based on the RoadNames and CityNames in the Road Data Set
     * 
     * @param Map<Integer, Road> roads - A Map containing loaded data from the Road Data Set*/
    public void initTrie(Map<Integer, Road> roads){
    	
    	for(Road r : roads.values()){
    		if( ! roadNames.contains(r.getLabel()))
    			roadNames.add(r.getLabel() +","+r.getCity());		//Get List of Unique Road Names + City
    		
    		prefixRoads.put(r.getLabel(), r);				//Initialize Data Struct with RoadNames as indexes to Roads
    	}
    	
    	for(String s : roadNames)
    		insert(s);										//Insert all RoadNames into Trie
    	
    }

	/**Inserts a Word (RoadName + CityName) into a Trie
	 * 
	 * @param String word*/
    public void insert(String word) {
      
    	HashMap<Character, TrieNode> children = root.children;
 
        for(int i = 0; i < word.length(); i++){
            
        	char c = word.charAt(i);
 
            TrieNode trieNode;
            
            if(children.containsKey(c))
            	trieNode = children.get(c);			//If it contains the current character on prefix, then set
            else{
                trieNode = new TrieNode(c);			//Else, create a new TrieNode and insert into Map
                children.put(c, trieNode);
            }
 
            children = trieNode.children;			//Set Children Nodes
 
            if(i == word.length()-1)
                trieNode.isRoad = true;    
        }
    }
 
 
    /**Returns true if there is an entry in the Trie that matches the given prefix
     * 
     * @param String prefix*/
    public boolean startsWith(String prefix) {
        
    	if(searchNode(prefix) == null) 
            return false;
        else
            return true;
        
    }
    
    /**Searches the particular Node that matches the given prefix
     * A match is found if the prefix matches an entry in the trie
     * up to the ith position.
     * 
     * @param String prefix*/
    public TrieNode searchNode(String prefix){
        
    	Map<Character, TrieNode> children = root.children; 
        TrieNode trieNode = null;
        
        for(int i = 0; i < prefix.length(); i++){
            
        	char c = prefix.charAt(i);
        	
            if(children.containsKey(c)){
                trieNode = children.get(c);
                children = trieNode.children;
            }
            else
                return null;
        }
 
        return trieNode;
    }


	/**Returns the list of Road objects that correspond to the given prefix
	 * 
	 * @param String prefix*/
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

	/**Returns a String that consists of all the roadNames and cityNames
	 * that match the given prefix
	 * 
	 * @param String prefix*/
	public String getRoadNames(String prefix) {

		StringBuilder sb = new StringBuilder();
		
		for(Road r : getRoads(prefix))
			sb.append("Street Name: " + r.getLabel() + "	City Name: " + r.getCity() + "\n");
		
		return sb.toString();
	}
    
	
}
