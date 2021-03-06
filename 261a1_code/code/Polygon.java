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
		
		this.color = colorIdentifier(type);
		//this.color = color.BLUE;
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
					line = br.readLine();
				else if(line.equals("[END]")){
						
					if(multiData.size() == 1){
						polygons.add(new Polygon(type, label, endLevel, cityIdx, data, null));		//Create new Polygon & Add to Collection of Polygons
						
						type = null; label = null;		//RESET 
						endLevel = 0; cityIdx = 0;
						multiData.clear();						
					}
					else if(multiData.size() > 1){					
						polygons.add(new Polygon(type, label, endLevel, cityIdx, null, multiData));		//Case for handling multiple data sets
						
						type = null; label = null; 		//RESET
						endLevel = 0; cityIdx = 0;
						multiData.clear();
					}
					
					line = br.readLine();
					line = br.readLine();
					line = br.readLine();
					
					if(line == null)				//EOF
						break;
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
						multiData.add(data);
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
		
		if(multiData == null){								//If Polygon only contains one data set of coordinates
			
			xPos = new int[data.size()];
			yPos = new int[data.size()];
			
			for(int i = 0; i < data.size(); i++){
				
				Point p = data.get(i).asPoint(origin, scale);
				Location pixelLoc = new Location(p.getX(), p.getY());
				Location pixelPos = pixelLoc.moveBy(offSetX, offSetY);
				
				xPos[i] = (int)pixelPos.x;
				yPos[i] = (int)pixelPos.y;				//Split Data into X and Y array of Coordinates
			}
			
			g2.drawPolygon(xPos, yPos, data.size());
		
			return;
		}
		else{
			
			for(List<Location> data : multiData){
				
				xPos = new int[data.size()];
				yPos = new int[data.size()];
				
				for(int i = 0; i < data.size(); i++){
					
					Point p = data.get(i).asPoint(origin, scale);
					Location pixelLoc = new Location(p.getX(), p.getY());
					Location pixelPos = pixelLoc.moveBy(offSetX, offSetY);

					xPos[i] = (int) pixelPos.x;
					yPos[i] = (int) pixelPos.y; 	// Split Data into X and Y array of Coordinates
				}
					
				g2.drawPolygon(xPos, yPos, data.size());
				
			}		
			return;
		}
	}
	
	private Color colorIdentifier(String type) {

		Color color = null;
		
		switch(type){
		case "0x01":
			color = new Color(Integer.decode(type));
			break;
		case "0x02":
			color = new Color(Integer.decode(type));
			break;
		case "0x03":
			color = new Color(Integer.decode(type));
			break;
		case "0x04":
			color = new Color(Integer.decode(type));
			break;
		case "0x05":
			color = new Color(Integer.decode(type));
			break;
		case "0x06":
			color = new Color(Integer.decode(type));
			break;
		case "0x07":
			color = new Color(Integer.decode(type));
			break;
		case "0x08":
			color = new Color(Integer.decode(type));
			break;
		case "0x09":
			color = new Color(Integer.decode(type));
			break;
		case "0x0a":
			color = new Color(Integer.decode(type));
			break;
		case "0x0b":
			color = new Color(Integer.decode(type));
			break;
		case "0x0c":
			color = new Color(Integer.decode(type));
			break;
		case "0x0d":
			color = new Color(Integer.decode(type));
			break;
		case "0x0e":
			color = new Color(Integer.decode(type));
			break;
		case "0x13":
			color = new Color(Integer.decode(type));
			break;
		case "0x14":
			color = new Color(Integer.decode(type));
			break;
		case "0x15":
			color = new Color(Integer.decode(type));
			break;
		case "0x16":
			color = new Color(Integer.decode(type));
			break;
		case "0x17":
			color = new Color(Integer.decode(type));
			break;
		case "0x18":
			color = new Color(Integer.decode(type));
			break;
		case "0x19":
			color = new Color(Integer.decode(type));
			break;
		case "0x1a":
			color = new Color(Integer.decode(type));
			break;
		case "0x1e":
			color = new Color(Integer.decode(type));
			break;
		case "0x1f":
			color = new Color(Integer.decode(type));
			break;
		case "0x20":
			color = new Color(Integer.decode(type));
			break;
		case "0x28":
			color = new Color(Integer.decode(type));
			break;
		case "0x29":
			color = new Color(Integer.decode(type));
			break;
		case "0x32":
			color = new Color(Integer.decode(type));
			break;
		case "0x3b":
			color = new Color(Integer.decode(type));
			break;
		case "0x3c":
			color = new Color(Integer.decode(type));
			break;
		case "0x3d":
			color = new Color(Integer.decode(type));
			break;
		case "0x3e":
			color = new Color(Integer.decode(type));
			break;
		case "0x3f":
			color = new Color(Integer.decode(type));
			break;
		case "0x40":
			color = new Color(Integer.decode(type));
			break;
		case "0x41":
			color = new Color(Integer.decode(type));
			break;
		case "0x42":
			color = new Color(Integer.decode(type));
			break;
		case "0x43":
			color = new Color(Integer.decode(type));
			break;
		case "0x44":
			color = new Color(Integer.decode(type));
			break;
		case "0x45":
			color = new Color(Integer.decode(type));
			break;
		case "0x46":
			color = new Color(Integer.decode(type));
			break;
		case "0x47":
			color = new Color(Integer.decode(type));
			break;
		case "0x48":
			color = new Color(Integer.decode(type));
			break;
		case "0x49":
			color = new Color(Integer.decode(type));
			break;
		case "0x4c":
			color = new Color(Integer.decode(type));
			break;
		case "0x4d":
			color = new Color(Integer.decode(type));
			break;
		case "0x4e": 
			color = new Color(Integer.decode(type));
			break;
		case "0x4f":
			color = new Color(Integer.decode(type));
			break;
		case "0x50":
			color = new Color(Integer.decode(type));
			break;
		case "0x51":
			color = new Color(Integer.decode(type));;
			break;
		case "0x52":
			color = new Color(Integer.decode(type));
			break;
		case "0x53":
			color = new Color(Integer.decode(type));
			break;
		}
		
		return color;
	}
}
