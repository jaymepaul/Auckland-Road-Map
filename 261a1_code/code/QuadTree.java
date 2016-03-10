import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;


public class QuadTree {

	private QuadNode root;
	
	private Map<Integer, Node> nodes;
	
	
	public QuadTree(Map<Integer, Node> nodes, Main main){
				
		this.nodes = nodes;

		initQuadTree(main);
	}
	
	public void initQuadTree(Main main){
		
		
		
		
		for(Node n : nodes.values()){
			
			
		}
	}
//	define a QuadTree class;
//	define a Node class;
//	implement a detail measuring function;
//	implement a construction algorithm;
//	implement an access algorithm.

	public Node getClosestNode(Location mouseLoc, Point mousePoint) {

		Node node = null;
		float threshold = 1;
		
		//0. Get list of Nodes that reside within a 10px radius of the mouseClick
		List<Node> selectNodes = new ArrayList<Node>();
		for(Node n : nodes.values()){
			if(mouseLoc.distance(n.getLocation()) <= 1.0)			//CHECK - PROPER RADIUS COORDS,
				selectNodes.add(n);
		}
		
		//1. Get mouseLoc coordinates, create QN, establish rect boundaries
		root = new QuadNode((int)mousePoint.getX(), (int)mousePoint.getY(), selectNodes);
		
		//2. Check if QN has only one Node in each quadrant
		if(measureDetail(root) < threshold)				//If it Returns true then can get closest node, else split
			node = root.getNode();
		else
			getClosestNode(mouseLoc);
			//recrusive
		
		
		
		return node;
	}
	
}
