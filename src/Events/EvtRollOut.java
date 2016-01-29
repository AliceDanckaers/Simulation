package Events;

import java.util.Random;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRollOut extends Events{

	public EvtRollOut() {
		this.name =  "Plane rolling out";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtRollOut(LogicalDateTime startDate, Entities plane) {
		this.name = "Plane rolling out";
		this.start = startDate;
		this.plane = plane;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "roulement sur taxiway 2 de l'avion "+plane.ID;
		if(aero.facilities.runway.status == "libre")
		{
			aero.facilities.runway.setStatus("occupe");
			int duration =rollOutDuration();
			agenda.add(new EvtRelease_TW2(start.add(LogicalDuration.ofMinutes(duration)),plane));
			agenda.add(new EvtTakeOff(start.add(LogicalDuration.ofMinutes(duration)),plane));

		}
		else
		{
			aero.waitingListTakeOff.add(plane);
		}
		return log;
	}
	private int rollOutDuration() {
		Random alea =new Random();
		int dur = 2+alea.nextInt(5);
		
		return dur;
	}


}
