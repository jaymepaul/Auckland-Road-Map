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

		for(Node n: main.getNodes()){					//Get each intersection of segment from Collection of Nodes
			if(n.getNodeID() == nodeID1)
				this.node1 = n;
			else if(n.getNodeID() == nodeID2)
				this.node2 = n;
		}
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
			
			RoadSegment segment = new RoadSegment(roadID, length, nodeID1, nodeID2, coords, main);		
			main.getSegments().add(segment);									//Add to Collection of Segments
			
			for (Node n : main.getNodes()) {
				if (n.getNodeID() == nodeID1 || n.getNodeID() == nodeID2)
					n.getSegments().add(segment);								//Add to Node's Collection of Segments
			}

			for (Road r : main.getRoads()) {
				if (r.getRoadID() == roadID)
					r.getSegments().add(segment); 								//Add to Road's Collection of Segments
			}
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

	public void setCoords(List<Location> coords) {
		this.coords = coords;
	}

}
