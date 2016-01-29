package BeNote;


import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class Entities implements Comparable<Entities>{

	public String name;
	public String type;
	public String status;
	public SortedList<Entities> planes;
	public int gate;
	public int ID;
	public LogicalDateTime arrived;
	
	public Entities(String name, String type, String status) { // constructor for aeroport facilities
		this.name = name;
		this.type = type;
		this.status = status;
	}
	
	public Entities(String name, String type) { // constructor for list of planes (does not have status)
		this.name = name;
		this.type = type;
		this.status = null;
		this.planes = new SortedList<Entities>();
	}
	
	public Entities(int planeID, String type) { // constructor for list of planes (does not have status)
		this.ID = planeID;
		this.type = type;

	}
	
	public void setStatus(String status){
		this.status = status;
	}

	@Override
	public int compareTo(Entities o) {
		return arrived.compareTo(o.arrived);
	}
	
}
