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
	private Location origin;									//Panning x Zooming Variables
	
	private Trie trie;											//Trie Data Structure containing all Road Names
	
	public Main() {
		
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.segments = new ArrayList<RoadSegment>();			//Initialize Collections/DataStructures
		
		this.offSetX = 0;
		this.offSetY = 0;
		this.scale = 100;												
		this.origin = Location.newFromLatLon(-36.847622, 174.763444);	//Initialize Scale & Shift Variables
		
	}

	@Override
	protected void redraw(Graphics g) {		
				
		for(Node n: nodes.values())
			n.drawNodes(g, origin, scale, offSetX, offSetY);				//Draw Nodes
		
		for(RoadSegment seg : segments)
			seg.drawSegments(g, scale, origin, offSetX, offSetY);		//Draw Segments
		
	}

	@Override
	protected void onClick(MouseEvent e) {

		StringBuilder info = new StringBuilder();										//String to store all info about intersection
		
		Point point = new Point(e.getX(), Math.abs(e.getY()));
		Location mouseLoc = Location.newFromPoint(point, origin, scale);				//Translate MousePos into a Location
		
		Node node = getClosestNode(mouseLoc, point);											//Get Node closest to MousePos				
		info.append("NodeID: " + Integer.toString(node.getNodeID()) + "\n");			//Get Intersection ID
		
		info.append("Roads at Intersection: \n");
		for(String s : node.getRoadsAtIntersect(this))
			info.append("Road: " + s + "\n");											//Get RoadNames of Roads connected to Intersection
		
		node.setColor(Color.RED);														//Highlight Node on GUI
		getTextOutputArea().setText(info.toString());									//Display Info on GUI
	}

	@Override
	protected void onSearch() {
		
		String prefix = getSearchBox().getText();								//Get User Input
		
		if(trie.startsWith(prefix))												//Search Trie using prefix
			highlightRoads(trie.getRoads(prefix));								//If matches are found then get Roads and highlight them
		
		getTextOutputArea().setText(prefix);									//Display RoadName on TextBox
		
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
			
			this.trie = new Trie(roads);									//Initialize Trie Structure
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Gets Node closest to MousePosition*/
	public Node getClosestNode(Location mouseLoc, Point pointLoc){
		
 		Node node = null;
		double dist = 1000;
		
		for(Node n: nodes.values()) 
			n.setColor(Color.BLACK);								//Reset Intersection Color
		
		for(Node n: nodes.values()){
					
//			if(n.getLocation().isClose(mouseLoc, dist))	{			//BUGED?: If distance is close then this becomes the closest Node
//				dist = n.getLocation().distance(mouseLoc);
//				node = n;
//			}
			if(Math.abs(n.getPixelPos().x - pointLoc.x) + Math.abs(n.getPixelPos().y - pointLoc.y) <= dist){
				dist = Math.abs(n.getPixelPos().x - pointLoc.x) + Math.abs(n.getPixelPos().y - pointLoc.y);
				node = n;
			}
		}	

		return node;
	}
	
	
	/**Highlights Road based on Sequence of Segments within that Road*/
	private void highlightRoads(List<Road> selectRoads){
		
		for(RoadSegment seg: segments)
			seg.setColor(Color.BLUE); 						//Reset Color to all Segments
		
		for(Road r : selectRoads){
			
			for(Road rd : r.getAllRoads(roads)){			//For each Road, get All Roads assoc. to it
				
				for(RoadSegment seg: rd.getSegments())		
					seg.setColor(Color.GREEN);				//Highlight each Segment within the Road
			}
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
