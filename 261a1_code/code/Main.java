import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends GUI {
	
	private Map<Integer, Node> nodes;
	private Map<Integer, Road> roads;
	private Map<Integer, RoadSegment> segments;
	

	public Main() {
		this.nodes = new HashMap<Integer, Node>();
		this.roads = new HashMap<Integer, Road>();
		this.segments = new HashMap<Integer, RoadSegment>();
	}

	@Override
	protected void redraw(Graphics g) {			//JUST BEFORE REDRAW, DATA STRUCTS RESET, NOT GETTING PASSED THROUGH
		
		Graphics2D g2 = (Graphics2D) g;
		
		double windowSize = this.getDrawingAreaDimension().getHeight() * this.getDrawingAreaDimension().getWidth();
		double scale;
		
		
		for(Map.Entry<Integer, Node> entry: nodes.entrySet()){			
			g2.setColor(Color.BLUE);
			g2.draw(new Line2D.Double(entry.getValue().getLocation().x,
									entry.getValue().getLocation().y,
									entry.getValue().getLocation().x,
									entry.getValue().getLocation().y));
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
