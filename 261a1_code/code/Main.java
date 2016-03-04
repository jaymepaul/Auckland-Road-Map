import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends GUI {
	
	private Map<Integer, Node> nodes;
	private Map<Integer, Road> roads;					
	private List<RoadSegment> segments;							//Data Structures, All Maps, Easily Access Values via KEYS
	
	private double scale;
	private int offSetX;
	private int offSetY;
	private Dimension dimension;
	private Location origin;									//Panning x Zooming Variables
	
	public Main() {
		
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.segments = new ArrayList<RoadSegment>();			//Initialize Collections/DataStructures
		
		this.offSetX = 0;
		this.offSetY = 0;
		this.scale = 100;	
		this.dimension = getDrawingAreaDimension();						//Initialize Scale & Shift Variables
		this.origin = Location.newFromLatLon(-36.847622, 174.763444);	//AUX Centre
	}

	@Override
	protected void redraw(Graphics g) {		
				
		for(Node n: nodes.values())
			n.drawNodes(g, dimension, origin, scale, offSetX, offSetY);				//Draw Nodes
		
		for(RoadSegment seg : segments)
			seg.drawSegments(g, dimension, scale, origin, offSetX, offSetY);		//Draw Segments
		
	}

	@Override
	protected void onClick(MouseEvent e) {

		StringBuilder info = new StringBuilder();										//String to store all info about intersection
		
		Point point = new Point(e.getX(), e.getY());
		Location mouseLoc = Location.newFromPoint(point, origin, scale);				//Translate MousePos into a Location
		
		Node node = getClosestNode(mouseLoc);											//Get Node closest to MousePos				
		info.append("NodeID: " + Integer.toString(node.getNodeID()) + "\n");			//Get Intersection ID
		
		info.append("Roads at Intersection: \n");
		for(String s : node.getRoadsAtIntersect(this))
			info.append("Road: " + s + "\n");											//Get RoadNames of Roads connected to Intersection
		
		node.setColor(Color.RED);														//Highlight Node on GUI
		getTextOutputArea().setText(info.toString());									//Display Info on GUI
	}

	@Override
	protected void onSearch() {

		String roadText = this.getSearchBox().getText();						//Get User Input
		List<Road> selectRoads = new ArrayList<Road>();							//List to contain all Roads of equal Name
		
		for(Road r : roads.values()){
			if(r.getLabel().equals(roadText))
				selectRoads.add(r);												//Add Road to List of Roads with equal Name
		}
		
		highlightRoad(selectRoads);												//Highlight Road on GUI
		getTextOutputArea().setText(roadText);									//Display RoadName on TextBox
		
	}

	@Override
	protected void onMove(Move m) {
		
		switch (m){
			case NORTH:
				offSetY -= 10;
				break;
			case SOUTH:
				offSetY += 10;
				break;
			case WEST:
				offSetX -= 10;
				break;
			case EAST:
				offSetX += 10;
				break;
			case ZOOM_IN:
				scale += 10;
				break;
			case ZOOM_OUT:
				scale -= 10;
				break;
		}	
			
	}

	@Override
	protected void onLoad(File nodesFile, File roadsFile, File segmentsFile, File polygonsFile) {
		
		try {
			Node.loadNodes(nodesFile, nodes);					
			Road.loadRoads(roadsFile, roads);
			RoadSegment.loadSegments(segmentsFile, segments, nodes, roads);					//Load Files
			//Polygon.loadPolygons(polygons);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Gets Node closest to MousePosition*/
	public Node getClosestNode(Location mouseLoc){
		
		Node node = null;
		double dist = 0;
		
		for(Node n: nodes.values()) 
			n.setColor(Color.BLACK);								//Reset Intersection Color
		
		for(Node n: nodes.values()){
			
			dist = n.getLocation().distance(mouseLoc);				//Calculate distance between actual Node and MouseLoc
			
			System.out.println("NX: "+n.getLocation().x+"NY"+n.getLocation().y);
			System.out.println("MX: "+mouseLoc.x+"MY"+mouseLoc.y);
			if(n.getLocation().isClose(mouseLoc, dist))				//If distance is close then this becomes the closest Node
				node = n;
			
		}	

		return node;
	}
	
	
	/**Highlights Road based on Sequence of Segments within that Road*/
	private void highlightRoad(List<Road> roads){
		
		for(RoadSegment seg: segments)
			seg.setColor(Color.BLUE); 						//Reset Color to all Segments
		
		for(Road r : roads){
			for(RoadSegment seg : r.getSegments())
				seg.setColor(Color.GREEN);					//Highlight selected Road
		}		
		
	}


	public static void main(String[] args) throws IOException {
		new Main();
	}

	public Map<Integer, Node> getNodes() {
		return nodes;
	}

	public Map<Integer, Road> getRoads() {
		return roads;
	}

	public List<RoadSegment> getSegments() {
		return segments;
	}

	
	
	
}
