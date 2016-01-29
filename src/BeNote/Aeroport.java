package BeNote;

import java.io.FileNotFoundException;
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
	public Entities entities;
	public String log;
	public String meteo;
	public static LogicalDuration dur;
	public static PrintWriter writer;

	// Airport parameters
	public Facilities facilities = new Facilities();
	public Entities planes;
	public SortedList<Entities> waitingListLanding = new SortedList<Entities>();
	public SortedList<Entities> waitingListGate = new SortedList<Entities>();
	public SortedList<Entities> waitingListTW2 = new SortedList<Entities>();
	public SortedList<Entities> waitingListTakeOff = new SortedList<Entities>();
	public EventsInitializer evtinit;
	public Aeroport(
			int i/* TODO aller chercher valeurs dans fichier parametre */) throws FileNotFoundException {

		// loading simulation parameters
		startDate = new LogicalDateTime("01/01/2015 00:00:00");
		stopDate = startDate.add(LogicalDuration.ofDay(2));
		currDate = startDate;
		log = "This is a log file";
		meteo = "bonne";
		writer = new PrintWriter("logger.txt");

		// loading airport parameters
		facilities.runway = new Entities("piste", "piste", "libre");
		facilities.taxiway1 = new Entities("taxiway 1", "taxiway", "libre");
		facilities.taxiway2 = new Entities("taxiway 2", "taxiway", "libre");
		facilities.gates = new Entities[i];
		for (int k = 0; k < i; k++) {
			facilities.gates[k] = new Entities("dock " + k, "dock", "libre");
		}
		planes = new Entities("avions", "avions");
		
		// loading schedueler
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
			numPlane = Integer.toString(currEvt.plane.ID);
		} catch (Exception e) {
			numPlane = "00";
		}

		// handling event
		String date = currDate.toString();
		String logmsg = date + ";" + numPlane + ";" + currEvt.doSomething(agenda, this);

		return logmsg;
	}

	public void printStatus(int i) {
		// for debugging
		System.out.println("---------------------------> status piste : " + this.facilities.runway.status);
		System.out.println("---------------------------> status tw 1 : " + this.facilities.taxiway1.status);
		for (int k = 0; k < i; k++) {
			System.out.println(
					"---------------------------> status gate " + (k + 1) + " : " + this.facilities.gates[k].status);
		}
		System.out.println("---------------------------> status tw 2 : " + this.facilities.taxiway2.status);
		System.out.println("---------------------------> meteo : " + this.meteo);

	}

	public static void main(String[] args) throws FileNotFoundException {
		// variables definition
			Aeroport BES = new Aeroport(4); // Brest Airport
			boolean endSimulation = false; // End of Simulation
			String logmsg; // Log message

		// Start of Simulation
		while (!endSimulation) {
			logmsg = BES.simulate();
			BES.log = BES.log + logmsg;
			if (BES.currDate.compareTo(BES.stopDate) > 0 || BES.agenda.size() == 0) {
				endSimulation = true;
			}
			writer.println(logmsg);
			//BES.printStatus(4);
		}
		writer.close();
	}

}