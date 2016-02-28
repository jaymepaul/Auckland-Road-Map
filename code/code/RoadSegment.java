import java.util.*;

public class RoadSegment {

	private int roadSegID;
	private double length;
	private Node node1;
	private Node node2;
	private List<Location> coords;		//List of co-ordinates along segment
	
	
	public RoadSegment(int roadSegID, double length, int nodeID1, int nodeID2, List<Location> coords){
		
		this.roadSegID = roadSegID;
		this.length = length;
		this.coords = coords;
		
		this.node1 = new Node(nodeID1, coords, true);				//First pair of co-ord
		this.node2  = new Node(nodeID2, coords, false);				//Last pair of co-ord
		
	}


	public int getRoadSegID() {
		return roadSegID;
	}


	public void setRoadSegID(int roadSegID) {
		this.roadSegID = roadSegID;
	}


	public List<Location> getCoords() {
		return coords;
	}


	public void setCoords(List<Location> coords) {
		this.coords = coords;
	}
	
	
}
