import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Polygon {

	private String type;
	private int endLevel;
	private int cityIdx;
	private List<Location> data;
	private int[] xPos;
	private int[] yPos;
	
	private Color color;
	
	public Polygon(String type, int endLevel, int cityIdx, List<Location> data){
		this.type = type;
		this.endLevel = endLevel;
		this.cityIdx = cityIdx;
		this.data = new ArrayList<Location>();
		
		this.color = Color.BLACK;
	}
	
	public static void loadPolygons(File file, List<Polygon> polygons) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			while((line = br.readLine()) != null){
				
				br.readLine();
				
				String type = br.readLine().substring(5);
				int endLevel = Integer.parseInt(br.readLine().substring(9));
				int cityIdx = Integer.parseInt(br.readLine().substring(8));
				
				List<Location> data = new ArrayList<Location>();
				String coords[] = br.readLine().substring(7).split("),(");
				
				for(String s : coords){
					String latLon[] = s.split(",");
					data.add(Location.newFromLatLon(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1])));
				}
				
				polygons.add(new Polygon(type, endLevel, cityIdx, data));		//Create new Polygon & Add to Collection of Polygons
				
				
				br.readLine();
				br.readLine();
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		br.close();
	}
	
	public void drawPolygons(Graphics g, Location origin, double scale, int offSetX, int offSetY ){
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		
		for(int i = 0; i < data.size(); i++){
			
			Point p = data.get(i).asPoint(origin, scale);
			Location pixelLoc = new Location(p.getX(), p.getY());
			Location pixelPos = pixelLoc.moveBy(offSetX, offSetY);
			
			this.xPos[i] = (int)pixelPos.x;
			this.yPos[i] = (int)pixelPos.y;				//Split Data into X and Y array of Coordinates
		}
		
		g2.fillPolygon(xPos, yPos, data.size());
	}
}
