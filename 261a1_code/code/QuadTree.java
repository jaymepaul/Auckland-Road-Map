import java.awt.Dimension;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;


public class QuadTree {

	private final int QN_NODE_CAPACITY = 4;
	
	private BoundingBox boundary;
	
	private List<Node> allNodes;
	private List<Node> points;
	
	private QuadTree NW;
	private QuadTree NE;
	private QuadTree SW;
	private QuadTree SE;
	
	public QuadTree(BoundingBox boundary){
		this.points = new ArrayList<Node>();
		this.boundary = boundary;
	}
	
	public QuadTree(BoundingBox boundary, List<Node> allNodes){
		
		this.boundary = boundary;
		this.allNodes = allNodes;
		this.points = new ArrayList<Node>();
		
		for(Node n : allNodes)
			insert(n);

	}


	public void insert(Node n){
		
		if(!boundary.containsNode(n))			//If Node is outside the boundaries of this quadrant, discard
			return;
		else if(points.size() < QN_NODE_CAPACITY){	//If there is space within this quad, add to this quads points
			points.add(n);
			return;
		}
		else if(NW == null)
			subdivide();						//Otherwise subdivide and expand tree
			
		//Check which quadrant will accept the point - CURRENTLY INSERTING INTO THE RIGHT QUADS
		if (NW.getBoundary().containsNode(n))
			NW.insert(n);
		if (NE.getBoundary().containsNode(n))
			NE.insert(n);
		if (SW.getBoundary().containsNode(n))
			SW.insert(n);
		if (SE.getBoundary().containsNode(n))
			SE.insert(n);
	}
	
	public void subdivide(){
		
		//Subdivide - Initialize Quadrants
		NW = new QuadTree(new BoundingBox(boundary.getX()/2, boundary.getY() + boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2));
		NE = new QuadTree(new BoundingBox(boundary.getX() + boundary.getX()/2, boundary.getY() + boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2));
		SW = new QuadTree(new BoundingBox(boundary.getX()/2, boundary.getY() - boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2));
		SE = new QuadTree(new BoundingBox(boundary.getX() + boundary.getX()/2, boundary.getY()/2 - boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2));
		
	}
	
	public List<Node> queryRange(BoundingBox rangeBoundary){
		
		List<Node> pointsInRange = new ArrayList<Node>();
		
		//Check if this quads boundary intersects this range
		if(!boundary.intersectsRange(rangeBoundary))
			return pointsInRange;			//Empty
		
		//Check objects at this quad level
		for(int i = 0; i < points.size(); i++){
			if(rangeBoundary.containsNode(points.get(i)))
				pointsInRange.add(points.get(i));
		}
		
		//Terminate here, if there are no children
		if(NW == null)
			return pointsInRange;
		
		//Else, Add Points from Children
		for(Node n : NW.queryRange(rangeBoundary))
			pointsInRange.add(n);
		for(Node n : NE.queryRange(rangeBoundary))
			pointsInRange.add(n);
		for(Node n : SW.queryRange(rangeBoundary))
			pointsInRange.add(n);
		for(Node n : SE.queryRange(rangeBoundary))
			pointsInRange.add(n);
		
		return pointsInRange;
	}
	
	public void setBoundary(BoundingBox boundary) {
		this.boundary = boundary;
	}


	public BoundingBox getBoundary() {
		return boundary;
	}


	public List<Node> getPoints() {
		return points;
	}
	
	
	
	
}
