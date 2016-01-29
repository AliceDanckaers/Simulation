package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public abstract class Events implements Comparable<Events>{
	public LogicalDateTime start ;
	public String name;
	public Entities plane;

	@Override
	public int compareTo(Events e) {
		return start.compareTo(e.start);
	}

	public abstract String doSomething(SortedList<Events> agenda, Aeroport aero);
	
}