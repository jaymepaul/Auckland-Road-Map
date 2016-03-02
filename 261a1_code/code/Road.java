import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	
	private Map<Integer, RoadSegment> segments;					//List of Road Segments
	
	
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
		
		this.segments = new HashMap<Integer, RoadSegment>();
	}
	
	public static void loadRoads(File file, Main main) throws IOException {
		
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
				
				main.getRoads().put(roadID , new Road(roadID, type, label, city, oneWay, speed, roadClass, notForCar, notForPede, notForBicycle));				//Add New Road to Collection of Roads
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
	}


	public int getRoadID() {
		return roadID;
	}


	public void setRoadID(int roadID) {
		this.roadID = roadID;
	}

	public Map<Integer, RoadSegment> getSegments() {
		return segments;
	}

	public String getLabel() {
		return label;
	}

	
	
	
	
}
