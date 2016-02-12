package Events;


import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtOncomingPlane extends Events  {

	public SortedList<Integer> waitingListLanding;
	public EvtOncomingPlane(){
		this.name =  "plane contacting control tower";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start;
		this.ID = 1;
	}
	
	public EvtOncomingPlane(LogicalDateTime startDate, Entities plane) {
		this.name =  "plane contacting control tower";
		this.start = startDate;
		this.plane = plane;
		this.plane.arrived = startDate;
		this.end = this.start;
		this.ID = 1;
	
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "L'avion "+plane.ID+" contacte la tour";
		aero.planes.planes.add(plane);
		
		int twID=0;
		boolean check = true;
		while(check)
		{
			if(twID>(aero.nb_taxiway-1))
			{
				// no free taxiway - wait
				check=false;
				aero.waitingListLanding.add(plane);
			}else
			{
				if(aero.facilities.runway.status == "libre" && aero.facilities.taxiway1[twID].status == "libre")
				{
					// free taxiway found - beginning landing
					check =false;
					this.plane.taxiway=twID;
					aero.facilities.runway.setStatus("occupe");
					aero.facilities.taxiway1[twID].setStatus("occupe");
					agenda.add(new EvtApproach(start,plane));
				}
				else
				{
					twID++;
				}
			}
		}
		return log;
	}



}
