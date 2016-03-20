import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;


public class RoadSegment {

	private int roadID;
	private double length;
	
	private Node node1;
	private Node node2;							// Intersections at each end of the Segment
	
	private List<Location> coords; 				// List of coordinates along segment
	
	private Color color;

	/**Road Segment constructor takes in the following parameters
	 * Immediately finds its Intersections and sets them N1, N2
	 * 
	 * @param int roadID, double length, int nodeID1, int nodeID2, List<Location> coords, Map<Integer, Node> nodes
	 * @return new RoadSegment obejct*/
	public RoadSegment(int roadID, double length, int nodeID1, int nodeID2, List<Location> coords, Map<Integer, Node> nodes) {

		this.roadID = roadID;
		this.length = length;
		this.coords = coords;

		this.node1 = nodes.get(nodeID1);				//Initialize the Segment's Nodes based on KEY/ID
		this.node2 = nodes.get(nodeID2);
	
		this.color = Color.BLUE;
	}

	/**Load RoadSegments from data files, 
	 * creates new RoadSegment objects and 
	 * adds them to the Collection of Segments
	 * 
	 * @param File file, List<RoadSegment segments, Map<Integer,Node> nodes, Map<Integer,Road> roads*/
	public static void loadSegments(File file, List<RoadSegment> segments, Map<Integer, Node> nodes, Map<Integer, Road> roads) throws IOException {

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
 				coords.add(Location.newFromLatLon(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));		//Load coordinates
			
			RoadSegment segment = new RoadSegment(roadID, length, nodeID1, nodeID2, coords, nodes);						//Create New Segment
			
			segments.add(segment);									//Add to Collection of Segments
			
			segment.getNode1().getSegments().add(segment);			//Add to Node's Collection of Segments
			segment.getNode2().getSegments().add(segment);
			
			segment.getNode1().getOutNeighbours().add(segment);
			segment.getNode2().getInNeighbours().add(segment);		//Update each Node's Neighbours
			
			roads.get(roadID).getSegments().add(segment);			//Add to Road's Collection of Segments
			
		}
		
		br.close();
	}
	
	/**Draws Segments based on location, shift, scale and origin
	 * 
	 * @param Graphics g, double scale, Location origin, int offSetX, int offSetY*/
	public void drawSegments(Graphics g , double scale, Location origin, int offSetX, int offSetY){
		
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(color);
		
		Point p1 = coords.get(0).asPoint(origin, scale);						//Translate location of Node into PixelCoord
		Point p2;
		
		for(int i = 1; i < coords.size(); i++){
			
			p2 = coords.get(i).asPoint(origin, scale);							//Translate location of Node into PixelCoord
			
			Location pixelL1 = new Location(p1.getX(), p1.getY());
			Location pixelP1 = pixelL1.moveBy(offSetX, offSetY);				
			
			Location pixelL2 = new Location(p2.getX(), p2.getY());
			Location pixelP2 = pixelL2.moveBy(offSetX, offSetY);					//Translate location of PixelCoord based on Panning movement
			
			g2.draw(new Line2D.Double(pixelP1.x , pixelP1.y, pixelP2.x, pixelP2.y));
			p1 = p2;																//Set N2 as N1 for next Segment
			
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
		return roadID;
	}

	public void setRoadSegID(int roadSegID) {
		this.roadID = roadSegID;
	}

	public List<Location> getCoords() {
		return coords;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
