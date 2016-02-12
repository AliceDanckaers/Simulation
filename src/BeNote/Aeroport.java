package BeNote;

import java.io.IOException;
import java.io.PrintWriter;
import Events.Events;
import Events.EventsInitializer;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class Aeroport {

	// simulation parameters
	public SortedList<Events> agenda = new SortedList<Events>();
	public Events currEvt;
	public LogicalDateTime currDate;
	public LogicalDateTime startDate;
	public LogicalDateTime stopDate;
	public String log;
	public String meteo;
	public static LogicalDuration dur;
	public static PrintWriter writer;


	// Airport parameters
	public Facilities facilities = new Facilities();
	public Entities planes;
	public int nb_gate;
	public int nb_taxiway;
	public SortedList<Entities> waitingListLanding = new SortedList<Entities>();
	public SortedList<Entities> waitingListGate = new SortedList<Entities>();
	public SortedList<Entities> waitingListTW2 = new SortedList<Entities>();
	public SortedList<Entities> waitingListTakeOff = new SortedList<Entities>();
	public EventsInitializer evtinit;
	public Aeroport() throws IOException {

	// loading simulation parameters
	SimulationGetPropertyValues properties = new SimulationGetPropertyValues();
	properties.getPropValues(this);
	currDate = startDate;
	writer = new PrintWriter("logger.csv");
		
	// loading airport parameters
	facilities.runway = new Entities("piste", "piste", "libre");
	facilities.taxiway1 = new Entities[nb_taxiway];
	facilities.taxiway2 = new Entities("taxiway 2", "taxiway", "libre");
	facilities.gates = new Entities[nb_gate];
	for (int k = 0; k < nb_gate; k++) {
		facilities.gates[k] = new Entities("dock " + k, "dock", "libre");
	}
	for (int k = 0; k < nb_taxiway; k++) {
		facilities.taxiway1[k] = new Entities("taxiway " +k, "taxiway In", "libre");
	}
	planes = new Entities("avions", "avions");
	
	// loading scheduler
	evtinit = new EventsInitializer(agenda, startDate, stopDate);		
		
	}

	public String simulate() {

		// get next event
		currEvt = agenda.first();
		agenda.remove(currEvt);

		// move clock forward
		currDate = currEvt.start;

		// Plane number
		String numPlane;

		try {
			numPlane = String.format("%02d", currEvt.plane.ID);
		} catch (Exception e) {
			numPlane = "00";
		}
		// handling event
		LogicalDuration delay;
		String logmsg;
		if(currEvt.ID!=2){
			logmsg = currDate + ";" + currDate.getDayOfWeek()+";"+ numPlane + ";" + currEvt.ID +";"+ currEvt.doSomething(agenda, this) +";"+currEvt.end+";"+currEvt.end.soustract(currDate).getMinutes()+" ; "+currEvt.end.soustract(currDate).getRestSeconds()+ "\n";
			
		}else{
			delay = currEvt.plane.delay;
			logmsg = currDate + ";" + currDate.getDayOfWeek()+";"+ numPlane + ";" + currEvt.ID +";"+ currEvt.doSomething(agenda, this) +";"+currEvt.end+";"+currEvt.end.soustract(currDate).getMinutes()+" ; "+currEvt.end.soustract(currDate).getRestSeconds()+ ";"+ delay.getMinutes() +"\n";
			
		}

		return logmsg;
	}

	public void printStatus() {
		// for debugging
		System.out.println("---------------------------> status piste : " + this.facilities.runway.status);
		for (int k = 0; k < this.nb_gate; k++) {
			System.out.println(
					"---------------------------> status gate " + (k + 1) + " : " + this.facilities.gates[k].status);
		}
		for (int k = 0; k < this.nb_taxiway; k++) {
			System.out.println(
					"---------------------------> status taxiway in " + (k + 1) + " : " + this.facilities.taxiway1[k].status);
		}
		System.out.println("---------------------------> status tw out : " + this.facilities.taxiway2.status);
		System.out.println("---------------------------> meteo : " + this.meteo);

	}

	public static void main(String[] args) throws IOException {
		// variables definition
			Aeroport BES = new Aeroport(); // Brest Airport
			boolean endSimulation = false; // End of Simulation
			String logmsg; // Log message
			System.out.println("begining of simulation");
		// Start of Simulation
		while (!endSimulation) {
			
			logmsg = BES.simulate();
			BES.log = BES.log + logmsg;
			if (BES.currDate.compareTo(BES.stopDate) > 0 || BES.agenda.size() == 0) {
				endSimulation = true;
			}

			//BES.printStatus(4);
		}
		writer.println(BES.log);
		writer.close();
		System.out.println("end of simulation, file logger.csv ready");
	}

}