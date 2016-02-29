import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends GUI {

	private List<Road> roads;
	private List<Node> nodes;
	private List<RoadSegment> segments;
	//private List<Polygon> polygons;

	public Main() {
		this.roads = new ArrayList<Road>();
		this.nodes = new ArrayList<Node>();
		this.segments = new ArrayList<RoadSegment>();
		//this.polygons = new ArrayList<Polygon>();
	}

	@Override
	protected void redraw(Graphics g) {
		
		for(Node n : nodes){
			g.setColor(Color.black);
			g.fillRect((int)n.getLocation().x, (int)n.getLocation().y, 30, 30);
		}
		
		for(RoadSegment seg: segments){
			g.setColor(Color.blue);
			g.drawLine((int)seg.getNode1().getLocation().x, (int)seg.getNode1().getLocation().y, (int)seg.getNode2().getLocation().x, (int)seg.getNode2().getLocation().y);		//Draw collection of Segments			
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

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<RoadSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<RoadSegment> segments) {
		this.segments = segments;
	}
	
	
}
