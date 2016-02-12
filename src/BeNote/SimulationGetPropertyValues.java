package BeNote;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class SimulationGetPropertyValues {

	String result = "";
	InputStream inputStream;
 
	public String getPropValues(Aeroport aero) throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			aero.startDate = new LogicalDateTime( prop.getProperty("startDate"));
			aero.stopDate = aero.startDate.add(LogicalDuration.ofDay( Integer.parseInt(prop.getProperty("duration"))));
			aero.log = "Simulation "+ prop.getProperty("name")+ " de l'aeroport de Brest\n"+" Date de debut ;Jour de la semaine; Numero de l'avion; Identifiant de l'evenement; Description de l'evenement; Date de fin;Duree (min); (seconds); Retard (min)\n";
			aero.meteo = prop.getProperty("meteo");
			aero.nb_gate = Integer.parseInt(prop.getProperty("nb_gate"));
			aero.nb_taxiway = Integer.parseInt(prop.getProperty("nb_taxiway"));
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
	
}
