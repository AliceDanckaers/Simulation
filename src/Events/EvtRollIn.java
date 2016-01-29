package Events;

import java.util.Random;

import BeNote.Aeroport;
import BeNote.Entities;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtRollIn extends Events{

	public EvtRollIn() {
		this.name =  "Plane rolling in";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}

	public EvtRollIn(LogicalDateTime startDate, Entities plane) {
		this.name = "Plane rolling in";
		this.start = startDate;
		this.plane = plane;
	}


	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		String log = "roulement sur taxiway1 de l'avion " +plane.ID;
		
		// Find a free gate
		int gateID=0;
		boolean check = true;
		while(check)
		{
			if(gateID>3)
			{
				// no free gate found - wait on taxiway
				check=false;
				aero.waitingListGate.add(plane);
				agenda.add(new EvtRelease_P(start.add(LogicalDuration.ofMinutes(rollInDuration()))));
			}else
			{
				if(aero.facilities.gates[gateID].status == "libre")
				{
					// free gate found - beginning unloading
					check =false;
					aero.facilities.gates[gateID].status = "occupe";
					plane.gate = gateID;
					int duration = rollInDuration();
					agenda.add(new EvtRelease_P_TW1(start.add(LogicalDuration.ofMinutes(duration))));
					agenda.add(new EvtUnload(start.add(LogicalDuration.ofMinutes(duration)),plane));
				}
				else
				{
					gateID++;
				}
			}
		}

		return log;
	}
	
	private int rollInDuration() {
		Random alea =new Random();
		int dur = 2+alea.nextInt(5);
		
		return dur;
	}
}
