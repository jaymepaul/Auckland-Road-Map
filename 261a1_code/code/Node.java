import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Node {

	private int nodeID;
	private double latitude;
	private double longitude;
	
	private Location location;
	private Map<Integer, RoadSegment> segments;				//List of segments attached to Node
	
	//Main Constructor for Node
	public Node(int nodeID, double latitude, double longitude){
		this.nodeID = nodeID;
		this.latitude = latitude;
		this.longitude = longitude;
		
		this.segments = new HashMap<Integer, RoadSegment>();	
		location = Location.newFromLatLon(latitude, longitude);			//Create new Location from lat, lon
	}

	
	/**Read Nodes from data files 
	 * @throws FileNotFoundException */
	public static void loadNodes(File file, Main main) throws FileNotFoundException{
					
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			while((line = br.readLine()) != null){
				
				StringTokenizer st = new StringTokenizer(line, "\t");			//Tokenizer separated by Tab
				int nodeID = Integer.parseInt(st.nextToken());
				double lat = Double.parseDouble(st.nextToken());
				double lon = Double.parseDouble(st.nextToken());
				
				main.getNodes().put(nodeID, new Node(nodeID, lat, lon));		//Create new node & add to Collection of Nodes
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<Integer, RoadSegment> getSegments() {
		return segments;
	}

	/**Returns maxLocation from Collection of Nodes*/
	public Location getMaxLoc(Main main) {
		
		double x = -1000, y = -1000;
		
		for(Map.Entry<Integer, Node> e: main.getNodes().entrySet()){
			if(e.getValue().getLocation().x > x)
				x = e.getValue().getLocation().x;
			if(e.getValue().getLocation().y > y)
				y = e.getValue().getLocation().y;
		}
		
		return new Location(x,y);
	}

	/**Returns minLocation from Collection of Nodes*/
	public Location getMinLoc(Main main) {
		
		double x = 1000, y = 1000;
		
		for(Map.Entry<Integer, Node> e: main.getNodes().entrySet()){
			if(e.getValue().getLocation().x < x)
				x = e.getValue().getLocation().x;
			if(e.getValue().getLocation().y < y)
				y = e.getValue().getLocation().y;
		}
		
		return new Location(x,y);
	}
	
	/**Returns List of RoadNames of Roads connected to Intersection*/
	public List<String> getRoadsAtIntersect(Main main){
		
		List<String> roadNames = new ArrayList<String>();
		
		for(Map.Entry<Integer, RoadSegment> entry: segments.entrySet()){
			
			String roadName = main.getRoads().get(entry.getValue().getRoadSegID()).getLabel();			//Get RoadName from Segment
			roadNames.add(roadName);
		}
		
		return roadNames;
	}
	
	

	

}
