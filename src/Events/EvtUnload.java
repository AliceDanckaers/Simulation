package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtUnload extends Events {

	
	public EvtUnload() {
		this.name =  "dechargement de l avion";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtUnload(LogicalDateTime startDate, Entities plane) {
		this.name = "phase d approche de l avion";
		this.start = startDate;
		this.plane = plane;

	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "debarquement des passagers avion "+plane.ID+" porte "+(plane.gate+1);
		System.out.println(log);
		agenda.add(new EvtRefueling(start.add(LogicalDuration.ofMinutes(10)),plane));
		return log;
	}	




}
