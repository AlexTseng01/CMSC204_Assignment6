
public class Road implements Comparable<Road> {

	private Town town1;
	private Town town2;
	private int distance;
	private String name;
	
	public Road(Town town1, Town town2, int distance, String name) {
		this.town1 = town1;
		this.town2 = town2;
		this.distance = distance;
		this.name = name;
	}
	
	public Town getTown1() {
		return town1;
	}

	public void setTown1(Town town1) {
		this.town1 = town1;
	}

	public Town getTown2() {
		return town2;
	}

	public void setTown2(Town town2) {
		this.town2 = town2;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return town1 + " " + town2 + " " + distance + " " + name;
	}

	@Override
	public int compareTo(Road o) {
		return this.name.compareTo(((Road)o).name);
	}
	
	@Override
	public boolean equals(Object o) {
		Road road = (Road)o;
		if (town1.getName().equals(road.getTown1().getName()) && town2.getName().equals(road.getTown2().getName()) ||
				town2.getName().equals(road.getTown1().getName()) && town1.getName().equals(road.getTown2().getName())) {
			return true;
		}
		return false;
	}

}
