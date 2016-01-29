package Events;

import java.util.Random;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtApproach extends Events {

	public EvtApproach() {
		this.name =  "Plane approaching phase";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.ID = 2;
	}
	
	public EvtApproach(LogicalDateTime startDate, Entities plane) {
		this.name = "Plane approaching phase";
		this.start = startDate;
		this.plane = plane;
		this.ID = 2;
	}

	@Override
	public String doSomething(SortedList<Events> agenda,Aeroport aero) {
		this.end = this.start.add(LogicalDuration.ofMinutes(approachDuration(aero.meteo)));
		String log = "approche de l'avion "+plane.ID;
		agenda.add(new EvtLanding(this.end,plane));
		return log;
	}

	private int approachDuration(String meteo) {
		Random alea =new Random();
		int dur = 2+alea.nextInt(4);
		if ( meteo== "mauvaise")
		{
			dur=2*dur;
		}
		return dur;
	}


}
