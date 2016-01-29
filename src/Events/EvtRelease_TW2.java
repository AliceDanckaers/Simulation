package Events;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRelease_TW2 extends Events {

	public EvtRelease_TW2() {
		this.name =  "Taxiway 2 free";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start;
		this.ID = 12;
	}

	public EvtRelease_TW2(LogicalDateTime startDate, Entities plane) {
		this.name =  "Taxiway 2 free";
		this.start = startDate;
		this.plane = plane;
		this.end = this.start;
		this.ID = 12;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "liberation du taxiway 2";
		if(aero.waitingListTW2.size()==0)
		{
			aero.facilities.taxiway2.status = "libre";
		}else
		{
			this.plane=aero.waitingListTW2.first();
			agenda.add(new EvtReleaseGate(start,plane.gate));
			agenda.add(new EvtRollOut(start,plane));
			aero.waitingListTW2.remove(plane);
		}
		return log;
	}


}
