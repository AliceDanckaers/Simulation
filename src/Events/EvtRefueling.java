package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRefueling extends Events{

	
	public EvtRefueling() {
		this.name =  "ravitaillement de l avion";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtRefueling(LogicalDateTime startDate, Entities plane) {
		this.name = "phase d approche de l avion";
		this.start = startDate;
		this.plane = plane;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "ravitaillement de l'avion "+plane.ID;
		System.out.println(log);
		agenda.add(new EvtLoading(start.add(LogicalDuration.ofMinutes(30)),plane));
		return log;
	}


}
