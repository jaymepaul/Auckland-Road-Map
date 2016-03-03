import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Node {

	private int nodeID;
	private double latitude;
	private double longitude;
	
	private Location location;
	private Map<Integer, RoadSegment> segments;				//List of segments attached to Node
	
	private Color color;									//Default Node Color
	
	//Main Constructor for Node
	public Node(int nodeID, double latitude, double longitude){
		this.nodeID = nodeID;
		this.latitude = latitude;
		this.longitude = longitude;
		
		this.color = Color.BLACK;
		this.segments = new HashMap<Integer, RoadSegment>();	
		this.location = Location.newFromLatLon(latitude, longitude);			//Create new Location from lat, lon
	}

	
	/**Read Nodes from data files 
	 * @throws IOException */
	public static void loadNodes(File file, Map<Integer, Node> nodes) throws IOException{
					
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			while((line = br.readLine()) != null){
				
				StringTokenizer st = new StringTokenizer(line, "\t");			//Tokenizer separated by Tab
				int nodeID = Integer.parseInt(st.nextToken());
				double lat = Double.parseDouble(st.nextToken());
				double lon = Double.parseDouble(st.nextToken());
				
				nodes.put(nodeID, new Node(nodeID, lat, lon));		//Create new node & add to Collection of Nodes
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
	}
	
	/**Returns List of RoadNames of Roads connected to Intersection*/
	public List<String> getRoadsAtIntersect(Main main){
		
		List<String> roadNames = new ArrayList<String>();
		
		for(RoadSegment seg: segments.values()){
			
			String roadName = main.getRoads().get(seg.getRoadSegID()).getLabel();			//Get RoadName from Segment
			roadNames.add(roadName);
		}
		
		return roadNames;
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

	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
	

	

}
