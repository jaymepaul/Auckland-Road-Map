import java.util.List;
import java.util.Map;

public class QuadNode {
	
	float threshold;			// Determines if each quadrant passes above or below the threshold
	int x, y, width, height;	// PixelPos x Rectangle Region Dimensions
	QuadNode UL, UR, LL, LR; 	// Four Quadrants/SubTrees
	List<Node> nodes; 			// List of Nodes that reside within the region of mouse click

	public QuadNode(int x, int y, int width, int height, List<Node> nodes) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.threshold = 1;
		
		this.nodes = nodes;
		initQuadrants();
	}

	public void initQuadrants(){
		
		 //upper left quadrant
	    UL = new QuadNode(data, x, y, width/2, height/2);

	    //upper right quadrant
	    UR = new QuadNode(data, x + width/2, y, width - width/2, height/2);

	    //lower left quadrant
	    LL = new QuadNode(data, x, y + height/2, width/2, height - height/2);

	    //lower right corner
	    LR = new QuadNode(data, x + width/2, y + height / 2, width - width/2, height - height/2);
	}
}
