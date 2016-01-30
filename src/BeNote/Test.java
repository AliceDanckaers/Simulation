package BeNote;

import Events.Events;
import Events.EvtOncomingPlane;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class Test {
	
	public static void main(String[] args) {
		LogicalDateTime currDate = new LogicalDateTime("09/03/2015 10:34:47.6789");
		LogicalDateTime startDate = new LogicalDateTime("10/12/2014 10:34:47.6789");
		System.out.println(currDate.compareTo( startDate.add( LogicalDuration.ofDay(90))));
		System.out.println(startDate.add( LogicalDuration.ofDay(90))); 
		Events currEvt =new EvtOncomingPlane(currDate, new Entities(1, "avion"));
		String toto = String.format("%02d", currEvt.plane.ID);
		System.out.println(toto); 
	}

}
