package Events;

import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRelease_P_TW1 extends Events {

	public EvtRelease_P_TW1() {
		this.name =  "Runway and Taxiway 1 free";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start;
		this.ID = 10;
	}
	
	public EvtRelease_P_TW1(LogicalDateTime startDate) {
		this.name = "Runway and Taxiway 1 free";
		this.start = startDate;
		this.end = this.start;
		this.ID = 10;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "liberation de la piste et du taxiway 1";
		if(aero.waitingListLanding.size()==0)
		{
			aero.facilities.runway.status = "libre";
			aero.facilities.taxiway1.status = "libre";
		}else
		{
			this.plane = aero.waitingListLanding.first();
			agenda.add(new EvtApproach(start,plane));
			aero.waitingListLanding.remove(plane);
		}
		return log;
	}



}
