import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Road {

	private String label, city;
	private int roadID, type, oneWay, speed, roadClass, notForCar, notForPede, notForBicycle;
	
	private List<RoadSegment> segments;					//List of Road Segments
	private List<Road> roads;							//List of All Roads associated with this Road
	
	public Road(int roadID, int type, String label, String city, int oneWay, int speed, int roadClass, int notForCar, int notForPede, int notForBicycle) {
		this.roadID = roadID;
		this.type = type;
		this.label = label;
		this.city = city;
		this.oneWay = oneWay;
		this.speed = speed;
		this.roadClass = roadClass;
		this.notForCar =  notForCar;
		this.notForPede = notForPede;
		this.notForBicycle = notForBicycle;
		
		this.segments = new ArrayList<RoadSegment>();
		this.roads = new ArrayList<Road>();
	}
	
	public static void loadRoads(File file, Map<Integer, Road> roads) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			br.readLine();
			while((line = br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line, "\t");			//Tokenizer separated by Tab
				int roadID = Integer.parseInt(st.nextToken());
				int type = Integer.parseInt(st.nextToken());
				String label = st.nextToken();
				String city = st.nextToken();
				int oneWay = Integer.parseInt(st.nextToken());
				int speed = Integer.parseInt(st.nextToken());
				int roadClass = Integer.parseInt(st.nextToken());
				int notForCar = Integer.parseInt(st.nextToken());
				int notForPede = Integer.parseInt(st.nextToken());
				int notForBicycle = Integer.parseInt(st.nextToken());
				
				roads.put(roadID , new Road(roadID, type, label, city, oneWay, speed, roadClass, notForCar, notForPede, notForBicycle));				//Add New Road to Collection of Roads
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
	}
	
	/**Get all Roads associated with this Road*/
	public List<Road> getAllRoads(Map<Integer, Road> mainRoads){
		
		for(Road r: mainRoads.values()){					//Go through all Roads, get ones that have equal Name
			if(r.getLabel().equals(this.label))
				roads.add(r);
		}
		
		return roads;
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

	public String getCity(){
		return city;
	}
	
	public String getLabel() {
		return label;
	}

	
	
	
	
}
