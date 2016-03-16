
public class BoundingBox {
	

	private double x,y;
	private double width, height;
	
	
	public BoundingBox(double x, double y, double width, double height){
		
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



	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	
	
	
}
