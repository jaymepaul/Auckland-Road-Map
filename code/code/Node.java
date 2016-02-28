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
		
		location = Location.newFromLatLon(latitude, longitude);			//Create new Location from lat, lon
	}
	
	//Alternative Constructor for Road Segment
	public Node(int nodeID, List<Location> coords, boolean nodeNo){
		
		Location loc;
		if(nodeNo)
			loc = coords.get(0);					//First Pair of Co-ord
		else
			loc = coords.get(coords.size());		//Last Pair of Co-ord
		
		this.nodeID = nodeID;
		this.location = loc;
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
