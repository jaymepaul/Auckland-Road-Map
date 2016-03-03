import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.*;
import java.util.*;


public class RoadSegment {

	private int roadSegID;
	private double length;
	private Node node1;
	private Node node2;
	private List<Location> coords; // List of co-ordinates along segment
	
	private Color color;

	public RoadSegment(int roadSegID, double length, int nodeID1, int nodeID2, List<Location> coords, Main main) {

		this.roadSegID = roadSegID;
		this.length = length;
		this.coords = coords;

		this.node1 = main.getNodes().get(nodeID1);				//Initialize the Segment's Nodes based on KEY/ID
		this.node2 = main.getNodes().get(nodeID2);
	
		this.color = Color.BLUE;
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
		
		br.close();
	}
	
	/**Draws Segments based on location, shift, scale and origin*/
	public void drawSegments(Graphics g, Dimension d, double scale, Location origin, int offSetX, int offSetY){
		
		g.setColor(color);
		
		Point p1 = coords.get(0).asPoint(origin, scale);
		Point p2;
		
		for(int i = 1; i < coords.size(); i++){
			
			p2 = coords.get(i).asPoint(origin, scale);
			
			int x1 = (int) ( (p1.getX() + offSetX) + (d.width * 0.5));
			int y1 = (int) ( (p1.getY() + offSetY)  + (d.height * 0.5));
			int x2 = (int) ( (p2.getX() + offSetX) + (d.width * 0.5));
			int y2 = (int) ( (p2.getY() + offSetY) + (d.height * 0.5));
			
			g.drawLine(x1, y1, x2, y2);
			p1 = p2;
			
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
