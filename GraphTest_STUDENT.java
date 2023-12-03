import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest_STUDENT {
	private GraphInterface<Town,Road> graph;
	private Town[] towns;

	@BeforeEach
	void setUp() throws Exception {
		graph = new Graph();
		towns = new Town[6];
		towns[0] = new Town("CrazyTown");
		towns[1] = new Town("CoolTown");
		towns[2] = new Town("NormalTown");
		towns[3] = new Town("asdfghjkl");
		towns[4] = new Town("SomeTown");
		towns[5] = new Town("RandomTown");
		
		graph.addVertex(towns[0]);
		graph.addVertex(towns[1]);
		graph.addVertex(towns[2]);
		graph.addVertex(towns[3]);
		graph.addVertex(towns[4]);
		graph.addVertex(towns[5]);
		
		graph.addEdge(towns[0], towns[1], 5000, "ALongRoad");
		graph.addEdge(towns[0], towns[3], 4999, "SlightlyShorterRoad");
		graph.addEdge(towns[0], towns[5], 5, "ARoad");
		graph.addEdge(towns[5], towns[4], 1, "OptimalRoad");
		graph.addEdge(towns[4], towns[2], 60, "WeirdRoad");
	}

	@AfterEach
	void tearDown() throws Exception {
		graph = null;
		towns = null;
	}

	@Test
	void testGetEdge() {
		assertEquals(new Road(towns[0], towns[1],5000,"ALongRoad"), graph.getEdge(towns[0], towns[1]));
		assertEquals(new Road(towns[0], towns[5],5,"ARoad"), graph.getEdge(towns[0], towns[5]));
	}

	@Test
	void testAddEdge() {
		assertFalse(graph.containsEdge(towns[0], towns[2]));
		graph.addEdge(towns[0], towns[2], 67, "AddedRoad");
		assertTrue(graph.containsEdge(towns[0], towns[2]));
	}

	@Test
	void testAddVertex() {
		assertFalse(graph.containsVertex(new Town("AddedTown")));
		graph.addVertex(new Town("AddedTown"));
		assertTrue(graph.containsVertex(new Town("AddedTown")));
	}

	@Test
	void testContainsEdge() {
		assertTrue(graph.containsEdge(towns[1], towns[0]));
	}

	@Test
	void testContainsVertex() {
		assertTrue(graph.containsVertex(towns[1]));
	}

	@Test
	void testEdgeSet() {
		Set<Road> roads = graph.edgeSet();
		assertTrue(roads.size() == 5);
	}

	@Test
	void testEdgesOf() {
		Set<Road> roads = graph.edgesOf(towns[0]);
		assertTrue(roads.size() == 3);
	}

	@Test
	void testRemoveEdge() {
		graph.removeEdge(towns[0], towns[1], 5000, "ALongRoad");
		Set<Road> roads = graph.edgesOf(towns[0]);
		assertTrue(roads.size() == 2);
	}

	@Test
	void testRemoveVertex() {
		graph.removeVertex(towns[5]);
		Set<Town> towns = graph.vertexSet();
		assertTrue(towns.size() == 5);
	}

	@Test
	void testVertexSet() {
		Set<Town> towns = graph.vertexSet();
		assertTrue(towns.size() == 6);
	}

	@Test
	void testShortestPath() {
		ArrayList<String> path = graph.shortestPath(towns[0], towns[4]);
		assertEquals("CrazyTown via ARoad to RandomTown 5 mi", path.get(0));
		assertEquals("RandomTown via OptimalRoad to SomeTown 1 mi", path.get(1));
	}

}
