import java.io.*;
import java.util.*;


public class RoadSegment {

	private int roadSegID;
	private double length;
	private Node node1;
	private Node node2;
	private List<Location> coords; // List of co-ordinates along segment

	public RoadSegment(int roadSegID, double length, int nodeID1, int nodeID2, List<Location> coords, Main main) {

		this.roadSegID = roadSegID;
		this.length = length;
		this.coords = coords;

		this.node1 = main.getNodes().get(node1);				//Initialize the Segment's Nodes based on KEY/ID
		this.node2 = main.getNodes().get(node2);
	}

	public static void loadSegments(File file, Main main) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		br.readLine();		
		while((line = br.readLine()) != null ){
			
			StringTokenizer st = new StringTokenizer(line, "\t");			//Tokenizer separated by Tab
			int roadID = Integer.parseInt(st.nextToken());
			double length = Double.parseDouble(st.nextToken());
			int nodeID1 = Integer.parseInt(st.nextToken());
			int nodeID2 = Integer.parseInt(st.nextToken());
 			List<Location> coords = new ArrayList<Location>();
			
			while(st.hasMoreTokens())
 				coords.add(new Location(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));		//Load coordinates
			
			RoadSegment segment = new RoadSegment(roadID, length, nodeID1, nodeID2, coords, main);		//Create New Segment
			
			main.getSegments().put(roadID, segment);									//Add to Collection of Segments
			
			if(main.getNodes().containsKey(nodeID1))								
				main.getNodes().get(nodeID1).getSegments().put(roadID, segment);
			else if(main.getNodes().containsKey(nodeID2))	
				main.getNodes().get(nodeID2).getSegments().put(roadID, segment);		//Add to Node's Collection of Segments
			
			if(main.getRoads().containsKey(roadID))
				main.getRoads().get(roadID).getSegments().put(roadID, segment);			//Add to Road's Collection of Segments
			
		}
	}
	
	public Node getNode1() {
		return node1;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public Node getNode2() {
		return node2;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
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


}
