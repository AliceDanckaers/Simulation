package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtLanding extends Events {

	public EvtLanding() {
		this.name =  "Plane landing";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}
	
	public EvtLanding(LogicalDateTime startDate, Entities plane) {
		this.name = "Plane landing";
		this.start = startDate;
		this.plane = plane;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "atterissage avion "+ plane.ID;
		agenda.add(new EvtRollIn(start.add(LogicalDuration.ofMinutes(2)),plane));
		return log;
	}
	


}
