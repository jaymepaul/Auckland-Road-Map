import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends GUI {
	
	private Map<Integer, Node> nodes;
	private Map<Integer, Road> roads;
	private List<Polygon> polygons;
	private List<RoadSegment> segments;							//Data Structures, Maps & Lists, Easily Access Values via KEYS
	
	private double scale;
	private int offSetX;
	private int offSetY;
	private Location origin;									//Panning x Zooming Variables
	
	private Trie trie;											//Trie Data Structure containing all Road Names
	private QuadTree quadTree;									//QuadTree Data Structure to accurately pinpoint Node postition
	
	public Main() {
		
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.polygons = new ArrayList<Polygon>();
		this.segments = new ArrayList<RoadSegment>();			//Initialize Collections/DataStructures
		
		this.offSetX = 0;
		this.offSetY = 0;
		this.scale = 10.0;												
		this.origin = new Location(-3,0);						//Initialize Scale & Shift Variables
		
	}

	@Override
	protected void onScroll(MouseWheelEvent e) {

		int zoomFactor = e.getWheelRotation();			//Neg - AWAY, Pos - TOWARDS
		
		if(zoomFactor > 0)
			scale -= 10;
		else if(zoomFactor < 0)
			scale += 10;								//Zooming
		
	}
	
	@Override
	protected void onMouseMove(MouseEvent e) {

		Dimension d = getDrawingAreaDimension();
		
		if(e.getX() >= 3 && e.getX() <= 30)
			offSetX += 10;
		if(e.getX() <= (d.getWidth()-5) && e.getX() >= (d.getWidth()-30))
			offSetX -= 10;
		if(e.getY() >= 3 && e.getY() <= 30)
			offSetY += 10;
		if(e.getY() <= (d.getHeight()-5) && e.getY() >= (d.getHeight()-30))
			offSetY -= 10;								//Pan depending on Mouse boundaries
	}
	
	@Override
	protected void redraw(Graphics g) {		
				
		for(Node n: nodes.values())
			n.drawNodes(g, origin, scale, offSetX, offSetY);			//Draw Nodes
		
		for(RoadSegment seg : segments)
			seg.drawSegments(g, scale, origin, offSetX, offSetY);		//Draw Segments
		
		for(Polygon p: polygons)
			p.drawPolygons(g, origin, scale, offSetX, offSetY);			//Draw Polygons
		
	}

	@Override
	protected void onClick(MouseEvent e) {
		
		Point p = getCentreLoc().asPoint(origin, scale);
		quadTree = new QuadTree(new BoundingBox(getCentreLoc().x, getCentreLoc().y,
				getMaxWidth()+0.1,getMaxHeight()+0.1), getNodesList()); 				//Initialize QuadTree
		
		StringBuilder info = new StringBuilder();										//String to store all info about intersection
		
		Point point = new Point(e.getX(), Math.abs(e.getY()));
		Location mouseLoc = Location.newFromPoint(point, origin, scale);				//Translate MousePos into a Location
		
		
		BoundingBox rangeBoundary = new BoundingBox(point.x, point.y, point.x+100, point.y+100);
		List<Node> selectNodes = quadTree.queryRange(rangeBoundary);	//Gets list of Nodes closest to click
//		Node node = getClosestNode(point, selectNodes);
		
		Node node = getClosestNode(mouseLoc, point);									//Get Node closest to MousePos				
		info.append("NodeID: " + Integer.toString(node.getNodeID()) + "\n");			//Get Intersection ID
		
		info.append("Roads at Intersection: \n");
		for(String s : node.getRoadsAtIntersect(roads))
			info.append("Road: " + s + "\n");											//Get RoadNames of Roads connected to Intersection
		
		node.setColor(Color.RED);														//Highlight Node on GUI
		getTextOutputArea().setText(info.toString());									//Display Info on GUI
	}

	@Override
	protected void onSearch() {
		
		String prefix = getSearchBox().getText();								//Get User Input
		
		if(trie.startsWith(prefix))												//Search Trie using prefix
			highlightRoads(trie.getRoads(prefix));								//If matches are found then get Roads and highlight them
		
		String roadNames = trie.getRoadNames(prefix);							//Get all RoadNames
		getTextOutputArea().setText(roadNames);									//Display RoadName on TextBox
		
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
			RoadSegment.loadSegments(segmentsFile, segments, nodes, roads);					
			
			if(polygonsFile != null)
				Polygon.loadPolygons(polygonsFile, polygons);					//Load All Files
			
			this.trie = new Trie(roads);										//Initialize Trie Structure
			
			
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

	public List<Node> getNodesList(){
		List<Node> data = new ArrayList<Node>();
		for(Node n: nodes.values())
			data.add(n);
		
		return data;
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
	
	
	/**Calculates maximum height - QuadTree dimensions
	 * 
	 * @return double maxHeight - in Location Units*/
	public double getMaxHeight(){
		
		List<Double> yLocs = new ArrayList<Double>();
		
		for(Node n : nodes.values())
			yLocs.add(n.getLocation().y);
		
		double max = Collections.max(yLocs);
		double min = Collections.min(yLocs);
		
		return Math.abs(max - min);
	}
	
	/**Calculates maximum width - QuadTree dimensions
	 * 
	 * @return double maxWidth - in Location Units*/
	public double getMaxWidth(){
		
		List<Double> xLocs = new ArrayList<Double>();
		
		for(Node n : nodes.values())
			xLocs.add(n.getLocation().x);
		
		double max = Collections.max(xLocs);
		double min = Collections.min(xLocs);
		
		return Math.abs(max - min);
	}
	
	/**Returns the exact centre Location of the dataSet - QuadTree Dimensions
	 * 
	 * @return Location - Initial Centre Location*/
	public Location getCentreLoc(){
		
		List<Double> xLocs = new ArrayList<Double>();
		
		for(Node n : nodes.values())
			xLocs.add(n.getLocation().x);
		
		double maxX = Collections.max(xLocs);
		double minX = Collections.min(xLocs);
		
		double centreX = minX + getMaxWidth()/2;
		
		
		List<Double> yLocs = new ArrayList<Double>();
		
		for(Node n : nodes.values())
			yLocs.add(n.getLocation().y);
		
		double maxY = Collections.max(yLocs);
		double minY = Collections.min(yLocs);
		
		double centreY = minY + getMaxHeight()/2;
		
		return new Location(centreX, centreY);
	}
}
