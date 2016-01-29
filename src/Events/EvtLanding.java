package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtLanding extends Events {

	public EvtLanding() {
		this.name =  "atterissage de l avion";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}
	
	public EvtLanding(LogicalDateTime startDate, Entities plane) {
		this.name = "phase d approche de l avion";
		this.start = startDate;
		this.plane = plane;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "atterissage avion "+ plane.ID;
		// TODO verifier duree atterissage
		agenda.add(new EvtRollIn(start.add(LogicalDuration.ofMinutes(2)),plane));
		System.out.println(log);
		return log;
	}
	


}
