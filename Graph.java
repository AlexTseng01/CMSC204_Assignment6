import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph implements GraphInterface<Town, Road> {

	private Set<Town> towns;
	private Set<Road> roads;
	
	private Map<Town, Integer> distances;
	private Map<Town, Town> predecessors;
	private Set<Town> unprocessed;
	
	private int townCount;
	private int roadCount;
	
	public Graph() {
		this.towns = new HashSet<>();
		this.roads = new HashSet<>();
		
		this.distances = new HashMap<>();
		this.predecessors = new HashMap<>();
		this.unprocessed = new HashSet<>();
		
		this.townCount = 0;
		this.roadCount = 0;
	}
	
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		// check if the list contains an edge with these requirements
		for (Road road : roads) {
			if (road.getTown1().equals(sourceVertex) && road.getTown2().equals(destinationVertex) ||
					road.getTown2().equals(sourceVertex) && road.getTown1().equals(destinationVertex)) {
				return road;
			}
		}
		return null;
	}

	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road road = new Road(sourceVertex, destinationVertex, weight, description);
		roads.add(road);
		roadCount++;
		return road;
	}

	@Override
	public boolean addVertex(Town v) {
		if (towns.contains(v)) {
			return false;
		}
		// successfully added
		towns.add(v);
		townCount++;
		return true;
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		for (Road road : roads) {
			if (road.getTown1().equals(sourceVertex) && road.getTown2().equals(destinationVertex) ||
					road.getTown2().equals(sourceVertex) && road.getTown1().equals(destinationVertex)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsVertex(Town v) {
		for (Town town : towns) {
			if (town.compareTo(v) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Road> edgeSet() {
		return roads;
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> adjacentRoads = new HashSet<>();
		for (Road road : roads) {
			if (road.getTown1().equals(vertex) || road.getTown2().equals(vertex)) {
				adjacentRoads.add(road);
			}
		}
		return adjacentRoads;
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road roadToRemove = getEdge(sourceVertex, destinationVertex);
		roads.remove(roadToRemove);
		roadCount--;
		return roadToRemove;
	}

	@Override
	public boolean removeVertex(Town v) {
		if (!towns.contains(v)) {
			return false;
		}
		towns.remove(v);
		townCount--;
		return true;
	}

	@Override
	public Set<Town> vertexSet() {
		return towns;
	}
	
	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		ArrayList<String> shortestPath = new ArrayList<>();
		dijkstraShortestPath(sourceVertex);
		
		Town currentTown = destinationVertex;
		// while there's a town to read
		while (currentTown != null) {
			Town previousTown = predecessors.get(currentTown);
			// if there is a predecessor
			if (previousTown != null) {
				Road currentRoad = getEdge(currentTown, previousTown);
				// add to beginning
				shortestPath.add(0, previousTown.getName() + " via " + currentRoad.getName() + 
						" to " + currentTown.getName() + " " + currentRoad.getDistance() + " mi");
				currentTown = previousTown;
			}
			else {
				break;
			}
		}
		
		return shortestPath;
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		// always retrieves the Town with the smallest distance
		Queue<Town> queue = new LinkedList<>();
		
		// each vertex has infinite distance and unprocessed except for the source
		for (Town town : towns) {
			distances.put(town, Integer.MAX_VALUE);
			unprocessed.add(town);
		}
		distances.put(sourceVertex, 0);
		queue.offer(sourceVertex);
		
		while (queue.size() != 0) {
			Town currentTown = queue.poll(); // always gets the town with the smallest distance
			
			// if the currentTown is already processed
			if (!unprocessed.contains(currentTown)) {
				continue;
			}
			
			// for every road in the currentTown
			Set<Road> currentRoads = edgesOf(currentTown);
			for (Road road : currentRoads) {
				Town nextTown = getNextTown(currentTown, road);
				int newDistance = road.getDistance() + distances.get(currentTown);
				
				if (!distances.containsKey(nextTown)) {
					continue;
				}
				
				if (newDistance < distances.get(nextTown)) {
					distances.put(nextTown, newDistance);
					predecessors.put(nextTown, currentTown); // currentTown is now behind nextTown
					queue.offer(nextTown);
				}
			}
		}
	}
	
	public Town getNextTown(Town currentTown, Road road) {
		if (currentTown.equals(road.getTown1())) {
			return road.getTown2();
		}
		else {
			return road.getTown1();
		}
	}
	
	public int getTownCount() {
		return this.townCount;
	}
	
	public int getRoadCount() {
		return this.roadCount;
	}

}
