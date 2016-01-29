package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRefueling extends Events{

	
	public EvtRefueling() {
		this.name =  "Plane refueling";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start.add(LogicalDuration.ofMinutes(30));
		this.ID = 6;
	}

	public EvtRefueling(LogicalDateTime startDate, Entities plane) {
		this.name =  "Plane refueling";
		this.start = startDate;
		this.plane = plane;
		this.end = this.start.add(LogicalDuration.ofMinutes(30));
		this.ID = 6;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "ravitaillement de l'avion "+plane.ID;
		agenda.add(new EvtLoading(this.end,plane));
		return log;
	}


}
