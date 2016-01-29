package BeNote;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import Events.Events;
import Events.EventsInitializer;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class Aeroport {

	// parametres de simulation
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

	// parametres de l'aeroport
	public Facilities facilities = new Facilities();
	public Entities planes;
	public SortedList<Entities> waitingListLanding = new SortedList<Entities>();
	public SortedList<Entities> waitingListGate = new SortedList<Entities>();
	public SortedList<Entities> waitingListTW2 = new SortedList<Entities>();
	public SortedList<Entities> waitingListTakeOff = new SortedList<Entities>();
	public EventsInitializer evtinit;
	public Aeroport(
			int i/* TODO aller chercher valeurs dans fichier parametre */) throws FileNotFoundException {

		// parametrage de la simulation
		startDate = new LogicalDateTime("01/01/2015 00:00:00");
		stopDate = startDate.add(LogicalDuration.ofDay(2));
		currDate = startDate;
		log = "This is a log file";
		meteo = "bonne";
		writer = new PrintWriter("logger.txt");

		// parametrage de l'aeroport (vide)
		facilities.runway = new Entities("piste", "piste", "libre");
		facilities.taxiway1 = new Entities("taxiway 1", "taxiway", "libre");
		facilities.taxiway2 = new Entities("taxiway 2", "taxiway", "libre");
		facilities.gates = new Entities[i];
		for (int k = 0; k < i; k++) {
			facilities.gates[k] = new Entities("dock " + k, "dock", "libre");
		}
		planes = new Entities("avions", "avions");

		// remplissage d'une partie de l'agenda
		// agenda.add(avion)
		// agenda.add(meteo)
		
		
		evtinit = new EventsInitializer(agenda, startDate, stopDate);
		
/*		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(1)), new Entities(12, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(20)), new Entities(3, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(2)), new Entities(57, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(15)), new Entities(4, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(32)), new Entities(8, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(153)), new Entities(99, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(254)), new Entities(7, "avion")));
		agenda.add(new EvtOncomingPlane(startDate.add(LogicalDuration.ofMinutes(246)), new Entities(34, "avion")));
		agenda.add(new EvtMeteoChange(startDate.add(LogicalDuration.ofMinutes(10)), "mauvaise"));
		agenda.add(new EvtMeteoChange(startDate.add(LogicalDuration.ofMinutes(30)), "bonne"));
		agenda.add(new EvtMeteoChange(startDate.add(LogicalDuration.ofMinutes(100)), "mauvaise"));
		agenda.add(new EvtMeteoChange(startDate.add(LogicalDuration.ofMinutes(180)), "bonne")); */
		
		
		
		
	}

	public String simulate() {

		// recupperation prochain evenement
		currEvt = agenda.first();
		agenda.remove(currEvt);

		// avancement de l'horloge
		currDate = currEvt.start;

		// num de l'avion
		String numPlane;

		try {
			numPlane = Integer.toString(currEvt.plane.ID);
		} catch (Exception e) {
			numPlane = "00";
		}

		// traitement de l'evenement
		String date = currDate.toString();
		String logmsg = date + ";" + numPlane + ";" + currEvt.doSomething(agenda, this);

		return logmsg;
	}

	public void printStatus(int i) {

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
		// definition des variables
		Aeroport BES = new Aeroport(4); // Aeroport de Brest
		boolean endSimulation = false; // fin de simulation
		String logmsg; // nouvel entree de log

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