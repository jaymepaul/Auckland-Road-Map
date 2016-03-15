
public class BoundingBox {
	
	private Location origin;
	private String quadrant;
	private int x, y, width, height;
	
	
	public BoundingBox(int x, int y, int width, int height){
		
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;

	}

	/**Checks if Node can be inserted within this boundary*/
	public boolean containsNode(Node n) {
		
		if(n.getLocation().x >= x-width/2 && n.getLocation().x <= x+width/2
				&& n.getLocation().y <= y+height/2 && n.getLocation().y >= y-height/2)
			return true;
		else 
			return false;
	}
	
	public boolean intersectsRange(BoundingBox range){
		
		boolean bool = false;
		
		if(range.x <= this.x + this.x/2 && range.y <= this.y + this.y/2
				&& range.x >= this.x - this.x/2 && range.y >= this.y - this.y/2)
			return true;
		
		return bool;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
