import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TownGraphManagerTest_STUDENT {
	private TownGraphManagerInterface graph;
	private String[] towns;
	
	@BeforeEach
	void setUp() throws Exception {
		graph = new TownGraphManager();
		towns = new String[6];
		
		towns[0] = "CrazyTown";
		towns[1] = "CoolTown";
		towns[2] = "NormalTown";
		towns[3] = "asdfghjkl";
		towns[4] = "SomeTown";
		towns[5] = "RandomTown";
		
		graph.addTown(towns[0]);
		graph.addTown(towns[1]);
		graph.addTown(towns[2]);
		graph.addTown(towns[3]);
		graph.addTown(towns[4]);
		graph.addTown(towns[5]);
		
		graph.addRoad(towns[0], towns[1], 5000, "ALongRoad");
		graph.addRoad(towns[0], towns[3], 4999, "SlightlyShorterRoad");
		graph.addRoad(towns[0], towns[5], 5, "ARoad");
		graph.addRoad(towns[5], towns[4], 1, "OptimalRoad");
		graph.addRoad(towns[4], towns[2], 60, "WeirdRoad");
	}

	@AfterEach
	void tearDown() throws Exception {
		graph = null;
		towns = null;
	}

	@Test
	void testAddRoad() {
		ArrayList<String> roads = graph.allRoads();
		assertTrue(roads.size() == 5);
		graph.addRoad(towns[1], towns[0], 1000000, "ReallyReallyRandomRoad");
		roads = graph.allRoads();
		assertTrue(roads.size() == 6);
		
	}

	@Test
	void testGetRoad() {
		assertEquals("WeirdRoad", graph.getRoad(towns[4], towns[2]));
	}

	@Test
	void testAddTown() {
		ArrayList<String> towns = graph.allTowns();
		assertTrue(towns.size() == 6);
		graph.addTown("AbsolutelyRandomTown");
		towns = graph.allTowns();
		assertTrue(towns.size() == 7);
	}

	@Test
	void testGetTown() {
		Town town = graph.getTown("CoolTown");
		assertEquals(towns[1], town.getName());
	}

	@Test
	void testContainsTown() {
		assertTrue(graph.containsTown("NormalTown"));
	}

	@Test
	void testContainsRoadConnection() {
		assertTrue(graph.containsRoadConnection(towns[0], towns[1]));
		assertFalse(graph.containsRoadConnection(towns[0], towns[2]));
	}

	@Test
	void testAllRoads() {
		ArrayList<String> roads = graph.allRoads();
		assertTrue(roads.size() == 5);
	}

	@Test
	void testDeleteRoadConnection() {
		graph.deleteRoadConnection(towns[0], towns[1], "ALongRoad");
		ArrayList<String> roads = graph.allRoads();
		assertTrue(roads.size() == 4);
	}

	@Test
	void testDeleteTown() {
		graph.deleteTown("NormalTown");
		ArrayList<String> towns = graph.allTowns();
		assertTrue(towns.size() == 5);
	}

	@Test
	void testAllTowns() {
		ArrayList<String> towns = graph.allTowns();
		assertTrue(towns.size() == 6);
	}

	@Test
	void testGetPath() {
		ArrayList<String> path = graph.getPath(towns[0], towns[4]);
		assertEquals("CrazyTown via ARoad to RandomTown 5 mi", path.get(0));
		assertEquals("RandomTown via OptimalRoad to SomeTown 1 mi", path.get(1));
	}

}
