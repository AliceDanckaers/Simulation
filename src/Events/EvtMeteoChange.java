package Events;

import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EvtMeteoChange  extends Events{
	public String meteo;
	
	public EvtMeteoChange() {
		this.name =  "changement de meteo";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
		this.end = this.start;
		this.ID = 14;
	}
	
	public  EvtMeteoChange(LogicalDateTime startDate, String meteo) {
		this.meteo =  meteo;
		this.start = startDate;
		this.name =  "changement de meteo";
		this.end = this.start;
		this.ID = 14;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		aero.meteo = this.meteo;
		String log = "la meteo devient "+aero.meteo;
		return log;
	}	



}
