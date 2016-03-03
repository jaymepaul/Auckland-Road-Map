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
	private Map<Integer, RoadSegment> segments;					//Data Structures, All Maps, Easily Access Values via KEYS
	
	private double scale;
	private int offSetX;
	private int offSetY;
	private Location origin;									//Panning x Zooming Variables
	
	public Main() {
		
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.segments = new HashMap<Integer, RoadSegment>();		//Initialize Collections/DataStructures
		
		offSetX = 0;
		offSetY = 0;
		scale = 100;												//Initialize Scale & Shift Variables
		origin = Location.newFromLatLon(-36.847622, 174.763444);	//AUX Centre
	}

	@Override
	protected void redraw(Graphics g) {		
				
		for(Node n: nodes.values())
			n.drawNodes(g, getDrawingAreaDimension(), origin, scale, offSetX, offSetY);
		
		for(Road road : roads.values()){
			for(RoadSegment seg : road.getSegments().values()){
				seg.drawSegments(g, getDrawingAreaDimension(), scale, origin, offSetX, offSetY);
			}
		}
					
	}

	@Override
	protected void onClick(MouseEvent e) {

		StringBuilder info = new StringBuilder();										//String to store all info about intersection
		
		Point point = new Point(e.getX(), e.getY());
		Location loc = Location.newFromPoint(point, origin, scale);						//Translate MousePos into Location
		
		Node node = getClosestNode(loc);												//Get Node closest to MousePos				
		info.append("NodeID: " + Integer.toString(node.getNodeID()) + "\n");			//Get Intersection ID
		
		info.append("Roads at Intersection: \n");
		for(String s : node.getRoadsAtIntersect(this))
			info.append("Road: " + s + "\n");											//Get RoadNames of Roads connected to Intersection
		
		node.setColor(Color.RED);														//Highlight Node on GUI
		getTextOutputArea().setText(info.toString());									//Display Info on GUI
	}

	@Override
	protected void onSearch() {

		String roadText = this.getSearchBox().getText();								//Get User Input
		
		for(Road r: roads.values()){
	
			if(r.getLabel().equals(roadText)){											//Get Road based on input
				
				highlightRoad(r.getSegments());											//Highlight Road on GUI
				getTextOutputArea().setText(roadText);									//Display RoadName on TextBox
				
			}			
		}
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
			Road.loadRoads(roadsFile, this);
			RoadSegment.loadSegments(segmentsFile, this);					//Load Files
			//Polygon.loadPolygons(polygons);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Gets Node closest to MousePosition*/
	public Node getClosestNode(Location mouseLoc){
		
		Node node = null;
		double dist = 1000;
		
		for(Node n: nodes.values()) 
			n.setColor(Color.BLACK);								//Reset Intersection Color
		
		for(Node n: nodes.values()){
			
//			dist = n.getLocation().distance(mouseLoc);				//Calculate distance between actual Node and MouseLoc
//			
//			if(n.getLocation().isClose(mouseLoc, dist))				//If distance is close then this becomes the closest Node
//				node = n;
			
			if(n.getLocation().distance(mouseLoc) <= dist){
				dist = n.getLocation().distance(mouseLoc);
				node = n;
			}
		}	
		
		return node;
	}
	
	
	/**Highlights Road based on Sequence of Segments within that Road*/
	private void highlightRoad(Map<Integer, RoadSegment> selectSegments){
		
		for(RoadSegment seg: segments.values())
			seg.setColor(Color.BLUE); 						//Reset Color to all Segments
		
		for(RoadSegment seg: selectSegments.values())
			seg.setColor(Color.GREEN);						//Highlight selected Road
		
	}
	
//	/**Set Scale Size*/
//	public void setScale(double windowSize){
//		
//		Location maxLoc = getMaxLoc();
//		Location minLoc = getMinLoc();								//Get Max/Min Locations from data set
//		scale = (windowSize / (maxLoc.distance(minLoc)));			//Calculate Scale
//	
//	}
	
	/**Returns maxLocation from Collection of Nodes*/
	public Location getMaxLoc() {
		
		double x = -1000, y = -1000;
		
		for(Node n: nodes.values()){
			if(n.getLocation().x > x)
				x = n.getLocation().x;
			if(n.getLocation().y > y)
				y = n.getLocation().y;
		}
		
		return new Location(x,y);
	}

	/**Returns minLocation from Collection of Nodes*/
	public Location getMinLoc() {
		
		double x = 1000, y = 1000;
		
		for(Node n: nodes.values()){
			if(n.getLocation().x < x)
				x = n.getLocation().x;
			if(n.getLocation().y < y)
				y = n.getLocation().y;
		}
		
		return new Location(x,y);
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

	public Map<Integer, RoadSegment> getSegments() {
		return segments;
	}

	
	
	
}
