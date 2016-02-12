package Events;


import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRelease_P extends Events{

	public EvtRelease_P() {
		this.name =  "Runway free";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start;
		this.ID = 13;
	}

	public EvtRelease_P(LogicalDateTime startDate) {
		this.name = "Runway free";
		this.start = startDate;
		this.end = this.start;
		this.ID = 13;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "liberation de la piste";
		int twID=0;
		boolean check = true;
		while(check)
		{
			if(twID>(aero.nb_taxiway-1))
			{
				// no free taxiway - wait
				check=false;
				twID=0;
			}else
			{
				if(aero.facilities.runway.status == "libre" && aero.facilities.taxiway1[twID].status == "libre")
				{
					// free taxiway found
					check =false;
				}
				else
				{
					twID++;
				}
			}
		}
		if(aero.facilities.taxiway1[twID].status == "libre" && aero.waitingListLanding.size()!=0) // priority on landing
		{
			agenda.add(new EvtRelease_P_TW1(start, twID));
		}
		else if(aero.waitingListTakeOff.size()==0)
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
