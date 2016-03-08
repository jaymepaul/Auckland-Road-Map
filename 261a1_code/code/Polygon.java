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
	private String label;
	private int endLevel;
	private int cityIdx;
	private List<Location> data;
	private List<List<Location>> multiData;
	
	private int[] xPos;
	private int[] yPos;
	
	private Color color;
	
	public Polygon(String type, String label, int endLevel, int cityIdx, List<Location> data, List<List<Location>> multiData){
		
		this.type = type;
		this.label = label;
		this.endLevel = endLevel;
		this.cityIdx = cityIdx;
		this.data = data;
		this.multiData = multiData;
		
		this.color = Color.BLACK;
	}
	
	public static void loadPolygons(File file, List<Polygon> polygons) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line;
		String type = null, label = null;
		int endLevel = 0, cityIdx = 0;
		
		String coords[];
		List<Location> data = null;
		List<List<Location>> multiData = new ArrayList<List<Location>>();
		
		try {
			while((line = br.readLine()) != null){
				
				if(line.equals("[POLYGON]"))
					br.readLine();
				else if(line.equals("[END]")){
						
					if(multiData.size() == 1){
						polygons.add(new Polygon(type, label, endLevel, cityIdx, data, null));		//Create new Polygon & Add to Collection of Polygons
						multiData.clear();						
						break;
					}
					else
						polygons.add(new Polygon(type, label, endLevel, cityIdx, null, multiData));
						multiData.clear();
					
					br.readLine();
					br.readLine();
				}
				
				String st[] = line.split("=");
				
				switch (st[0]){
				
					case "Type":
						type = st[1].toString();
						break;
					case "EndLevel":
						endLevel = Integer.parseInt(st[1]);
						break;
					case "Label":
						label = st[1].toString();
						break;
					case "CityIdx":
						cityIdx = Integer.parseInt(st[1]);
						break;
					
					case "Data0":
						data = new ArrayList<Location>();
						coords = st[1].split(",");	
						
						for(int i = 0; i < coords.length; i++){
								double x = Double.parseDouble(coords[i].substring(1));
								double y = Double.parseDouble(coords[i+1].substring(0, coords[i+1].length()-1));
								data.add(Location.newFromLatLon(x, y));
								i++;
						}
						multiData.add(data);
						break;
						
					case "Data1":
						data = new ArrayList<Location>();
						coords = st[1].split(",");	
						
						for(int i = 0; i < coords.length; i++){
								double x = Double.parseDouble(coords[i].substring(1));
								double y = Double.parseDouble(coords[i+1].substring(0, coords[i+1].length()-1));
								data.add(Location.newFromLatLon(x, y));
								i++;
						}
						break;
				}
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
