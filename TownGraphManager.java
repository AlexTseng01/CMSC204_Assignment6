import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class TownGraphManager implements TownGraphManagerInterface {

	private Graph graph;
	
	public TownGraphManager() {
		graph = new Graph();
	}
	
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		// track previous road count
		int temp = graph.getRoadCount();
		
		graph.addEdge(getTown(town1), getTown(town2), weight, roadName);
		// if current roadCount doesn't change
		if (graph.getRoadCount() == temp) {
			return false;
		}
		return true;
	}

	@Override
	public String getRoad(String town1, String town2) {
		Road road = graph.getEdge(getTown(town1), getTown(town2));
		return road.getName();
	}

	@Override
	public boolean addTown(String v) {
		Town town = new Town(v);
		return graph.addVertex(town);
	}

	@Override
	public Town getTown(String name) {
		Set<Town> towns = graph.vertexSet();
		
		for (Town town : towns) {
			if (town.getName().equals(name)) {
				return town;
			}
		}
		return null;
	}

	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return graph.containsEdge(new Town(town1), new Town(town2));
	}

	@Override
	public ArrayList<String> allRoads() {
		Set<Road> roads = graph.edgeSet();
		ArrayList<String> roadList = new ArrayList<>();
		
		for (Road road : roads) {
			roadList.add(road.getName());
		}
		
		Collections.sort(roadList);
		return roadList;
	}

	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		if (graph.removeEdge(new Town(town1), new Town(town2), 0, road) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTown(String v) {
		return graph.removeVertex(getTown(v));
	}

	@Override
	public ArrayList<String> allTowns() {
		Set<Town> towns = graph.vertexSet();
		ArrayList<String> townList = new ArrayList<>();
		
		for (Town town : towns) {
			townList.add(town.getName());
		}
		
		Collections.sort(townList);
		return townList;
	}

	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		return graph.shortestPath(getTown(town1), getTown(town2));
	}
	
	public void populateTownGraph(File file) throws FileNotFoundException {
		// format: roadName, distance; town1; town2
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		
		Scanner scan = new Scanner(file);
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] data = line.split("[,;]");
			
			String roadName = data[0];
			int distance = Integer.parseInt(data[1]);
			String town1 = data[2];
			String town2 = data[3];
			
			addTown(town1);
			addTown(town2);
			addRoad(town1, town2, distance, roadName);
			
		}
		
		scan.close();
	}

}
