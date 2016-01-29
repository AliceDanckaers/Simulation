package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtLoading extends Events{

	
	public EvtLoading() {
		this.name =  "phase d embarquement de l avion";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtLoading(LogicalDateTime startDate, Entities plane) {
		this.name = "phase d approche de l avion";
		this.start = startDate;
		this.plane = plane;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "embarquement de l'avion "+plane.ID+" porte "+(plane.gate+1) ;
		System.out.println(log);
		if(aero.facilities.taxiway2.status == "libre")
		{
			aero.facilities.taxiway2.setStatus("occupe");
			agenda.add(new EvtReleaseGate(start.add(LogicalDuration.ofMinutes(20)), plane.gate));
			agenda.add(new EvtRollOut(start.add(LogicalDuration.ofMinutes(20)),plane));
		}
		else
		{
			aero.waitingListTW2.add(plane);
		}

		return log;
	}	



}
