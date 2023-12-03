import java.util.ArrayList;

public class Town implements Comparable<Town> {

	private String name;
	private ArrayList<Town> adjacentTowns;
	
	public Town(String name) {
		this.name = name;
		this.adjacentTowns = new ArrayList<Town>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Town> getAdjacentTowns() {
		return adjacentTowns;
	}
	
	public void setAdjacentTowns(ArrayList<Town> adjacentTowns) {
		this.adjacentTowns = adjacentTowns;
	}
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public int compareTo(Town o) {
		return this.name.compareTo(o.name);
	}
	
	public void addAdjacentTown(Town town) {
		this.adjacentTowns.add(town);
	}
	
	public void removeAdjacentTown(Town town) {
		this.adjacentTowns.remove(town);
	}
	
	@Override
	public boolean equals(Object o) {
		Town town = (Town)o;
		if (name.equals(town.getName())) {
			return true;
		}
		return false;
	}
	
}
