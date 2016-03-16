import java.awt.Dimension;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;


public class QuadTree {

	private final int QN_NODE_CAPACITY = 4;		//Max capacity for each quadNode
	private boolean isLeaf;						//Determine whether quadNode isLeaf (Contains points of data) OR an internal node (Has quadNode children but no data)
	
	private BoundingBox boundary;				//Boundary object assigned to each node, establishes a boundary around node to segregate data points appropriately
	private List<Node> points;					//List of data points
	
	private QuadTree NW;						
	private QuadTree NE;
	private QuadTree SW;
	private QuadTree SE;						//Children
	
	
	/**Creates a new QuadTree node, establishes a BoundingBox
	 * and determines whether it is a leaf or an internal node
	 * 
	 * @param BoundingBox boundary
	 * @param boolean isLeaf*/
	public QuadTree(BoundingBox boundary, boolean isLeaf){
		this.points = new ArrayList<Node>();
		this.boundary = boundary;
		this.isLeaf = isLeaf;
	}
	
	/**Creates a new QuadTree, establishes a BoundingBox and 
	 * populates the tree with all the data points/intersections
	 * 
	 * @param BoundingBox boundary
	 * @param List<Node> allNodes*/
	public QuadTree(BoundingBox boundary, List<Node> allNodes){
		
		this.boundary = boundary;
		this.points = new ArrayList<Node>();
		this.isLeaf = false;
		
		subdivide();		//Initialize Quads
		
		for(Node n : allNodes){
			System.out.println(n.getNodeID() +"	"+ n.getLocation().x +"	"+ n.getLocation().y);
			insert(n);
		}

	}


	public void insert(Node n){
		
		
		if(points.size() < QN_NODE_CAPACITY){		//If there is space within this quad, add to this quads points
			points.add(n);
			System.out.println("INSERTED");
			return;
		}
		else{
			
			System.out.println("SUBDIVIDING.. ");
			subdivide();							//Otherwise subdivide and expand tree
			
			//Check which quadrant will accept the point
				if (NW.getBoundary().containsNode(n))
					NW.insert(n);
				if (NE.getBoundary().containsNode(n))
					NE.insert(n);
				if (SW.getBoundary().containsNode(n))
					SW.insert(n);
				if (SE.getBoundary().containsNode(n))
					SE.insert(n);
			
			isLeaf = false;				//Has Children
		}
		
	}
	
	public void subdivide(){
		
		//Subdivide - Initialize Quadrants
		NW = new QuadTree(new BoundingBox(boundary.getX()/2, boundary.getY() + boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2), false);
		NE = new QuadTree(new BoundingBox(boundary.getX() + boundary.getX()/2, boundary.getY() + boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2), false);
		SW = new QuadTree(new BoundingBox(boundary.getX()/2, boundary.getY() - boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2), false);
		SE = new QuadTree(new BoundingBox(boundary.getX() + boundary.getX()/2, boundary.getY()/2 - boundary.getY()/2, boundary.getWidth()/2, boundary.getHeight()/2), false);
		
		
		//Split list of current points into appropriate quadrants
		for(Node n : points){
			if (NW.getBoundary().containsNode(n)){
				NW.getPoints().add(n);
				NW.setLeaf(true);
			}
			if (NE.getBoundary().containsNode(n)){
				NE.getPoints().add(n);
				NE.setLeaf(true);
			}
			if (SW.getBoundary().containsNode(n)){
				SW.getPoints().add(n);
				SW.setLeaf(true);
			}
			if (SE.getBoundary().containsNode(n)){
				SE.getPoints().add(n);
				SE.setLeaf(true);
			}
		}
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

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
	
	
}
