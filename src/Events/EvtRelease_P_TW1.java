package Events;

import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class EvtRelease_P_TW1 extends Events {

	public EvtRelease_P_TW1() {
		this.name =  "piste et Taxiway 1 libres";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		
	}
	
	public EvtRelease_P_TW1(LogicalDateTime startDate) {
		this.name = "phase d approche de l avion";
		this.start = startDate;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "liberation de la piste et du taxiway 1";
		System.out.println(log);
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
