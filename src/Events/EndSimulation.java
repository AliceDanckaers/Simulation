package Events;

import BeNote.Aeroport;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class EndSimulation extends Events  {

	public EndSimulation(){
		this.name =  "arrivee de l avion";
		this.start =  new LogicalDateTime("01/01/2015 00:00:00.0000");
	}
	public EndSimulation(LogicalDateTime stopDate) {
		this.start = stopDate;
	}

	@Override
	public String doSomething(SortedList<Events> agenda, Aeroport aero) {
		System.out.println("Fin de Simulation");
		return null;
	}

}
