package Events;

import java.time.DayOfWeek;

import BeNote.Entities;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.SortedList;

public class EventsInitializer {

	public SortedList<Events> agenda;
	public LogicalDateTime startDate;
	public LogicalDateTime stopDate;
	public LogicalDateTime evtDate;
	public MoreRandom random = new MoreRandom(MoreRandom.globalSeed);

	public EventsInitializer(SortedList<Events> agenda, LogicalDateTime startDate, LogicalDateTime stopDate) {
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.agenda = agenda;

		generatePlanes(startDate);
		generateMeteo(startDate);
	}

	private void generateMeteo(LogicalDateTime startDate) {
		boolean beauTemps = true;
		evtDate = startDate;
		long offset;
		while (evtDate.compareTo(stopDate) < 0) {
			if (beauTemps) {
				offset = (long) random.nextExp(1/(7 * 24.));
				agenda.add(new EvtMeteoChange(evtDate, "mauvaise"));
				beauTemps = !beauTemps;
				evtDate = evtDate.add(LogicalDuration.ofHours(offset));
			} else {
				offset = (long) random.nextExp(1/24.);
				agenda.add(new EvtMeteoChange(evtDate, "bonne"));
				evtDate = evtDate.add(LogicalDuration.ofHours(offset));
			}

		}

	}

	private void generatePlanes(LogicalDateTime startDate) {
		
		evtDate = startDate;

		int NFlight = 1;
		long offset;
		while (evtDate.compareTo(stopDate) < 0) {
			
			if (evtDate.getHour() < 7) { /* Probably useless (and bugged) */
				evtDate = (evtDate.truncateToDays()).add(LogicalDuration.ofHours(8));

			} else if ((evtDate.getHour() > 22)) {

				evtDate = (evtDate.truncateToDays()).add(LogicalDuration.ofHours(31));
			} else if ((evtDate.getDayOfWeek() == DayOfWeek.SATURDAY) | (evtDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {

				offset = (long) random.nextExp(1/40.);
				evtDate = evtDate.add(LogicalDuration.ofMinutes(offset));
				agenda.add(new EvtOncomingPlane(evtDate, new Entities(NFlight, "avion")));
				NFlight += 1;

			} else if ((evtDate.getHour() > 7 & evtDate.getHour() < 10)
					| (evtDate.getHour() > 17 & evtDate.getHour() < 19)) {
				
				offset = (long) random.nextExp(1/10.);
	
				evtDate = evtDate.add(LogicalDuration.ofMinutes(offset));
				agenda.add(new EvtOncomingPlane(evtDate, new Entities(NFlight, "avion")));
				NFlight += 1;
			} else {
				offset = (long) random.nextExp(1/20.);
				evtDate = evtDate.add(LogicalDuration.ofMinutes(offset));
				agenda.add(new EvtOncomingPlane(evtDate, new Entities(NFlight, "avion")));
				NFlight += 1;
			}
			if(NFlight == 100)
			{
				NFlight = 1;
			}
		}

	}

}
