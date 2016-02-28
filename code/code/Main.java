import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.sun.org.apache.bcel.internal.generic.IFNE;

public class Main extends GUI {

	private List<Road> roads;
	private List<Node> nodes;
	private List<File> dataFiles;
	
	public Main(List<File> dataFiles) {
		this.dataFiles = dataFiles;
	}
	
	//Read Data based on the list of data files given
	public void readData(List<File> dataFiles) throws IOException{
		
		for(File file : dataFiles){
			
			switch (file.getName()){
			case "nodeID-lat-lon.tab":
				loadNodes(file);
			case "roadID-roadInfo.tab":
				loadRoads(file);
			case "roadSeg-roadID-length-nodeID-nodeID-coords.tab":
				loadSegments(file);
			}
		}
	}
	
	/**Read Nodes from data files 
	 * @throws FileNotFoundException */
	public void loadNodes(File file) throws FileNotFoundException{
					
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			while((line = br.readLine()) != null){
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){
			nodes.add(new Node(sc.nextInt(), sc.nextDouble(), sc.nextDouble()));
			sc.nextLine();
		}
		
	}
	
	private void loadRoads(File file) throws FileNotFoundException {
		
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){
			roads.add(new Road(sc.nextInt(), sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
			sc.nextLine();
		}
		
	}
	
	private void loadSegments(File file) throws FileNotFoundException {
		
		int roadID;
		double length;
		int nodeID1;
		int nodeID2;
		List<Location> coords = new ArrayList<Location>();
		
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){
			
			roadID = sc.nextInt();
			length = sc.nextDouble();
			nodeID1 = sc.nextInt();
			nodeID2 = sc.nextInt();
			
			while(sc.hasNext())				
				coords.add(new Location(sc.nextDouble(), sc.nextDouble()));
			
			sc.nextLine();
			
			RoadSegment segment = new RoadSegment(roadID, length, nodeID1, nodeID2, coords);		//Create New Segment
			
			for(Node n : nodes){										
				if(n.getNodeID() == nodeID1 || n.getNodeID() == nodeID2)
					n.getSegments().add(segment);													//Add Segment to adjacent nodes
			}
			
			for(Road r: roads){
				if(r.getRoadID() == roadID)
					r.getSegments().add(segment);													//Add Segment to Road
			}
			
		}
	}



	@Override
	protected void redraw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String []args) throws IOException{
		
		System.out.println("Select Data Size \nType 'LARGE' or 'SMALL':");
		Scanner sc = new Scanner(System.in);
		String size = sc.next().toLowerCase();
		
		File file = new File(".\\data\\data\\" + size);
		List<File> dataFiles = new ArrayList<File>();
		
		for(String s: file.list())							//Create List of Files depending on data size
			dataFiles.add(new File(s));
		
		new Main(dataFiles);					
		
	}
}
