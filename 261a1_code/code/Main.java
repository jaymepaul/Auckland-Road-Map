import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends GUI {
	
	private Map<Integer, Node> nodes;
	private Map<Integer, Road> roads;
	private Map<Integer, RoadSegment> segments;					//Data Structures, All Maps, Easily Access Values via KEYS
	
	private double scale;
	private Location origin;
	private Location maxLoc;
	private Location minLoc;									//Scaling and Origin Location Variables, Panning x Zooming
	
	private Graphics graphics;									//Graphics handler
	
	
	public Main() {
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.segments = new HashMap<Integer, RoadSegment>();
	}

	@Override
	protected void redraw(Graphics g) {		
				
		graphics = g;
		Point point, p1, p2;
		double windowSize = 850;					
		
		//========================DRAW NODES============================//
		
		for(Map.Entry<Integer, Node> entry: nodes.entrySet()){	
			
			origin = new Location(0,0);												//CHECK: Calculate Origin Location
			
			maxLoc = entry.getValue().getMaxLoc(this);
			minLoc = entry.getValue().getMinLoc(this);								//Get Max/Min Locations from data set
			scale = (windowSize / (maxLoc.distance(minLoc)));						//Calculate Scale
			
			point = entry.getValue().getLocation().asPoint(origin, scale);			//Translate Location to Pixel Co-Ordinates
			
			g.setColor(Color.BLACK);
			g.drawOval(point.y, point.x, 3, 3);
			g.fillOval(point.y, point.x, 3, 3);										//Draw Node based on Pixel Co-Ordinates		
			
		}
		
		//========================DRAW EDGES=============================//
		
		for(Map.Entry<Integer, RoadSegment> entry: segments.entrySet()){
			
			p1 = entry.getValue().getNode1().getLocation().asPoint(origin, scale);
			p2 = entry.getValue().getNode2().getLocation().asPoint(origin, scale);
			
			g.setColor(Color.BLUE);
			g.drawLine(p1.y, p1.x, p2.y, p2.x);											//Draw Edges from N1 to N2
			
			//===============CHECK==============//
			
			for(int i = 0; i < entry.getValue().getCoords().size()-1; i++){				//CHECK: Draw Edges for ALL Co-Ordinates on Segment??
						
				p1 = entry.getValue().getCoords().get(i).asPoint(origin, scale);
				p2 = entry.getValue().getCoords().get(i + 1).asPoint(origin, scale);	//Get Pixel Co-Ordinates of current loc and next loc
				
				g.setColor(Color.BLUE);
				g.drawLine(p1.y, p1.x, p2.y, p2.x);										//Draw Edges
				
			}
			
			//=============CHECK===============//
			
		}
	}

	@Override
	protected void onClick(MouseEvent e) {

		StringBuilder info = new StringBuilder();										//String to store all info about intersection
		
		Point point = new Point(e.getX(), e.getY());
		Location loc = Location.newFromPoint(point, origin, scale);						//Translate MousePos into Location
		
		Node node = this.getClosestNode(loc);											//Get Node closest to MousePos				
		info.append("NodeID: " + Integer.toString(node.getNodeID()) + "\n");			//Get Intersection ID
		
		info.append("Roads at Intersection: \n");
		for(String s : node.getRoadsAtIntersect(this))
			info.append("Road: " + s + "\n");											//Get RoadNames of Roads connected to Intersection
		
		highlightIntersection(node, graphics);											//Highlight Node on GUI
		this.getTextOutputArea().setText(info.toString());								//Display Info on GUI
	}

	@Override
	protected void onSearch() {

		

	}

	@Override
	protected void onMove(Move m) {
		
		switch (m){
			case NORTH:
				break;
			case SOUTH:
				break;
			case WEST:
				break;
			case EAST:
				break;
			case ZOOM_IN:
				break;
			case ZOOM_OUT:
				break;
		}	
			
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		
		try {
			Node.loadNodes(nodes, this);					
			Road.loadRoads(roads, this);
			RoadSegment.loadSegments(segments, this);					//Load Files
			//Polygon.loadPolygons(polygons);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Gets Node closest to MousePosition*/
	public Node getClosestNode(Location mouseLoc){
		
		Node node = null;
		double dist = 10000;
		
		for(Map.Entry<Integer, Node> entry : this.nodes.entrySet()){
			
			if(mouseLoc.distance(entry.getValue().getLocation()) < dist){
				dist = mouseLoc.distance(entry.getValue().getLocation());
				node = entry.getValue();										//Find Node - shortest distance from mouseClick to actual Node
			}	
		}	
		
		return node;
	}
	
	/**Highlight the given Node on GUI*/
	private void highlightIntersection(Node node, Graphics g) {
		
		Point point = node.getLocation().asPoint(origin, scale);			//Translate Location to Pixel Co-Ordinates
		
		g.setColor(Color.YELLOW);
		g.drawOval(point.y, point.x, 3, 3);
		g.fillOval(point.y, point.x, 3, 3);									//Highlight Node based on Pixel Co-Ordinates
		
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
