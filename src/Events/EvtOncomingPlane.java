package Events;


import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class EvtOncomingPlane extends Events  {

	public SortedList<Integer> waitingListLanding;
	public EvtOncomingPlane(){
		this.name =  "plane contacting control tower";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	}
	
	public EvtOncomingPlane(LogicalDateTime startDate, Entities plane) {
		this.name =  "plane contacting control tower";
		this.start = startDate;
		this.plane = plane;
		this.plane.arrived = startDate;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "L'avion "+plane.ID+" contacte la tour";
		aero.planes.planes.add(plane);
		if(aero.facilities.runway.status == "libre" && aero.facilities.taxiway1.status == "libre")
		{
			aero.facilities.runway.setStatus("occupe");
			aero.facilities.taxiway1.setStatus("occupe");
			agenda.add(new EvtApproach(start,plane));
		}
		else
		{
			aero.waitingListLanding.add(plane);
		}
		return log;
	}



}
