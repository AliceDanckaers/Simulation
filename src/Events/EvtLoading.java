package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtLoading extends Events{

	
	public EvtLoading() {
		this.name =  "Plane Loading";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start.add(LogicalDuration.ofMinutes(20));
		this.ID = 7;
	}

	public EvtLoading(LogicalDateTime startDate, Entities plane) {
		this.name =  "Plane Loading";
		this.start = startDate;
		this.plane = plane;
		this.end = this.start.add(LogicalDuration.ofMinutes(20));
		this.ID = 7;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "embarquement de l'avion "+plane.ID+" porte "+(plane.gate+1) ;
		if(aero.facilities.taxiway2.status == "libre")
		{
			aero.facilities.taxiway2.setStatus("occupe");
			agenda.add(new EvtReleaseGate(this.end, plane.gate));
			agenda.add(new EvtRollOut(this.end,plane));
		}
		else
		{
			aero.waitingListTW2.add(plane);
		}

		return log;
	}	



}
