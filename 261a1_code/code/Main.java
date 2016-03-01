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
	private Map<Integer, RoadSegment> segments;
	
	private double scale;
	private Location maxLoc;
	private Location minLoc;
	
	
	public Main() {
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.segments = new HashMap<Integer, RoadSegment>();
	}

	@Override
	protected void redraw(Graphics g) {		
		
		Location origin;		
		Point point, p1, p2;
		double windowSize = 850;					
		
		//========================DRAW NODES============================//
		
		for(Map.Entry<Integer, Node> entry: nodes.entrySet()){	
			
			origin = new Location(0,0);												//Calculate Origin Location
			
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
			
			origin = new Location(0,0);
			
			p1 = entry.getValue().getNode1().getLocation().asPoint(origin, scale);
			p2 = entry.getValue().getNode2().getLocation().asPoint(origin, scale);
			
			g.setColor(Color.BLUE);
			g.drawLine(p1.y, p1.x, p2.y, p2.x);
			
		}
	}

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub

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
