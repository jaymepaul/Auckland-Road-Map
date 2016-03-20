import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Node {

	private int nodeID;
	private double latitude;
	private double longitude;
	
	private Location location;
	private List<RoadSegment> segments;											//List of Segments attached to Node
	
	private List<RoadSegment> outNeighbours;
	private List<RoadSegment> inNeighbours;										//Collections to store IN and OUT edges from Node
	
	private Color color;
	private Point pos;
	
	
	/**Node constructor, takes in a nodeID, latitude & longitude. 
	 * Automatically converts lat, lon to a Location value.
	 * Initializes List of Segments to obtain segments connected to this Node.  
	 * 
	 * @param int nodeID, double latitude, double longitude
	 * @return new Node Object based on given parameters*/
	public Node(int nodeID, double latitude, double longitude){
		this.nodeID = nodeID;
		this.latitude = latitude;
		this.longitude = longitude;
		
		this.color = Color.BLACK;
		this.segments = new ArrayList<RoadSegment>();	
		this.location = Location.newFromLatLon(latitude, longitude);
		
		this.outNeighbours = new ArrayList<RoadSegment>();
		this.inNeighbours = new ArrayList<RoadSegment>();				
	}

	
	/**Read all Node data from data files and parse them into Node objects,
	 * add each Node to the Collection of Nodes 
	 *
	 * @param File file, Map<Integer, Node> nodes 
	 * @throws IOException */
	public static void loadNodes(File file, Map<Integer, Node> nodes) throws IOException{
					
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			while((line = br.readLine()) != null){
				
				StringTokenizer st = new StringTokenizer(line, "\t");			//Tokenizer separated by Tab
				int nodeID = Integer.parseInt(st.nextToken());
				double lat = Double.parseDouble(st.nextToken());
				double lon = Double.parseDouble(st.nextToken());
				
				nodes.put(nodeID, new Node(nodeID, lat, lon));		//Create new node & add to Collection of Nodes
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
	}
	
	/**Draws Nodes based on location, shift, scale and origin
	 * 
	 * @param Graphics g, Location origin, double scale, int offSetX, int offSetY*/
	public void drawNodes(Graphics g, Location origin, double scale, int offSetX, int offSetY){
		
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		
		Point p = location.asPoint(origin, scale);							//Translate location of Node into PixelCoord
		Location pixelLoc = new Location(p.getX(), p.getY());
		Location pixelPos = pixelLoc.moveBy(offSetX, offSetY);				//Translate location of PixelCoord based on Panning movement
		
		pos = new Point((int)pixelPos.x - 1, (int)pixelPos.y - 1);
		g2.fill(new Ellipse2D.Double(pixelPos.x - 1, pixelPos.y - 1, 3, 3));
						
	}
	
	/**Returns List of RoadNames of Roads connected to Intersection
	 * 
	 * @return List<String> - list of road names at the intersection*/
	public List<String> getRoadsAtIntersect(Map<Integer, Road> roads){
		
		List<String> roadNames = new ArrayList<String>();
		
		for(RoadSegment seg: segments){
			
			String roadName = roads.get(seg.getRoadSegID()).getLabel();			//Get RoadName from Segment
			
			if(!roadNames.contains(roadName))									//Only Add Unique Names
				roadNames.add(roadName);
			
		}
		
		return roadNames;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<RoadSegment> getSegments() {
		return segments;
	}

	public Color getColor() {
		return color;
	}
	
	public Point getPixelPos(){
		return pos;
	}


	public List<RoadSegment> getOutNeighbours() {
		return outNeighbours;
	}


	public List<RoadSegment> getInNeighbours() {
		return inNeighbours;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
	

	

}
