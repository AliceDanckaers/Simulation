package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtTakeOff extends Events {

	public EvtTakeOff() {
		this.name =  "Plane taking off";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start.add(LogicalDuration.ofMinutes(3));
		this.ID = 9;
	}

	public EvtTakeOff(LogicalDateTime startDate, Entities plane) {
		this.name = "Plane taking off";
		this.start = startDate;
		this.plane = plane;
		this.end = this.start.add(LogicalDuration.ofMinutes(3));
		this.ID = 9;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "decolage de l'avion "+plane.ID;
		agenda.add(new EvtRelease_P(this.end));
		aero.planes.planes.remove(plane);
		return log;
	}



}
