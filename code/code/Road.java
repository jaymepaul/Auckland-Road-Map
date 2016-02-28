import java.util.*;

public class Road {

	private int roadID;
	private int type;
	private String label;
	private String city;
	private int oneWay;
	private int speed;
	private int roadClass;
	private int notForCar;
	private int notForPede;
	private int notForBicycle;
	
	private List<RoadSegment> segments;					//List of Road Segments
	
	
	public Road(int roadID, int type, String label, String city, int speed, int roadClass, int notForCar, int notForPede, int notForBicycle) {
		this.roadID = roadID;
		this.type = type;
		this.label = label;
		this.city = city;
		this.speed = speed;
		this.roadClass = roadClass;
		this.notForCar =  notForCar;
		this.notForPede = notForPede;
		this.notForBicycle = notForBicycle;
	}


	public int getRoadID() {
		return roadID;
	}


	public void setRoadID(int roadID) {
		this.roadID = roadID;
	}


	public List<RoadSegment> getSegments() {
		return segments;
	}


	public void setSegments(List<RoadSegment> segments) {
		this.segments = segments;
	}
	
	
	
}
