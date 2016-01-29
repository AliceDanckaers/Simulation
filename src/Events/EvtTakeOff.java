package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtTakeOff extends Events {

	public EvtTakeOff() {
		this.name =  "decollage de l avion";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtTakeOff(LogicalDateTime startDate, Entities plane) {
		this.name = "phase d approche de l avion";
		this.start = startDate;
		this.plane = plane;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "decolage de l'avion "+plane.ID;
		System.out.println();
		agenda.add(new EvtRelease_P(start.add(LogicalDuration.ofMinutes(3))));
		aero.planes.planes.remove(plane);
		return log;
	}



}
