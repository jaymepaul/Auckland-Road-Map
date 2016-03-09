

public class QuadNode {

	double x, y;				// PixelPos
	QuadNode chidlren[]; 		// Four Quadrants/SubTrees
	Node node; 					// Intersection Object

	public QuadNode(double x, double y, Node node) {
		this.x = x;
		this.y = y;
		this.node = node;
	}

}
