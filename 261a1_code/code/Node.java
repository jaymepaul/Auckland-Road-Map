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
	private List<RoadSegment> segments;				//List of segments attached to Node
	
	//Main Constructor for Node
	public Node(int nodeID, double latitude, double longitude){
		this.nodeID = nodeID;
		this.latitude = latitude;
		this.longitude = longitude;
		
		this.segments = new ArrayList<RoadSegment>();
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
				
				main.getNodes().add(new Node(nodeID, lat, lon));				//Add New Node to Collection
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<RoadSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<RoadSegment> segments) {
		this.segments = segments;
	}

	public void addSegment(int nodeID, RoadSegment segment){
		segments.add(segment);
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

}
