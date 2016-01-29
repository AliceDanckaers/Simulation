package Events;

import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class EvtMeteoChange  extends Events{
	public String meteo;
	
	public EvtMeteoChange() {
		this.name =  "changement de meteo";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	
	}
	
	public  EvtMeteoChange(LogicalDateTime startDate, String meteo) {
		this.meteo =  meteo;
		this.start = startDate;
		this.name =  "changement de meteo";
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		aero.meteo = this.meteo;
		System.out.println("la meteo devient "+aero.meteo);
		return null;
	}	



}
