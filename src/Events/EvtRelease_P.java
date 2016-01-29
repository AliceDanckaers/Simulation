package Events;


import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class EvtRelease_P extends Events{

	public EvtRelease_P() {
		this.name =  "liberation de la piste";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtRelease_P(LogicalDateTime startDate) {
		this.name = "phase d approche de l avion";
		this.start = startDate;

	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "liberation de la piste";
		System.out.println(log);
		if(aero.waitingListTakeOff.size()==0)
		{
			aero.facilities.runway.status = "libre";
		}else
		{
			this.plane = aero.waitingListTakeOff.first();
			agenda.add(new EvtRelease_TW2(start,plane));
			agenda.add(new EvtTakeOff(start,plane));
			aero.waitingListTakeOff.remove(plane);
		}
		return log;
	}


}
