/**
* Classe BasicLoggerImpl.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base.utility.loggerimpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Properties;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.utility.ILogger;
import enstabretagne.base.utility.LogLevels;
import enstabretagne.base.utility.Settings;
import enstabretagne.simulation.components.ScenarioId;

public class BasicLoggerImpl implements ILogger{

	//BufferedWriter writer;
	FileWriter fw;
	
	final char sep = Settings.sep();

	String dirname;
	File logDir;
	String filename;
	File logFile;
	
	public BasicLoggerImpl() {
		
		dirname = System.getProperty("user.dir") + "\\log";
		filename = dirname + "\\Test.txt";
		logDir = new File(dirname);
		logFile=new File(filename);
		
		System.out.println(logFile);
	}
	
	@Override
	public boolean open(HashMap<String,Object> initParams) {
		boolean result = false;
		
		boolean res=true;
		
		if(!logDir.exists())
				res = logDir.mkdirs();
		
		
		if(res){
			
			try {
				res= logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			fw=new FileWriter(logFile,true);
			//writer = new BufferedWriter(fw);
			result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void log(ScenarioId scenarioId,Temporal t, LogicalDateTime d, LogLevels level, Object obj,String function,
			String message, Object... args) {
		
			try {
				fw.write(String.format(scenarioId.getScenarioId()+sep+scenarioId.getRepliqueNumber()+sep+t.toString() + sep + d + sep + level + sep + obj + sep + function+sep+message,args));
				fw.write("\n");
				fw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void close() {
		if(fw==null) return;
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}

	@Override
	public void clear(HashMap<String,Object> initParams) {
		if(logFile.exists()) {
				close();
				logFile.delete();
				open(initParams);
		}
		else
			open(initParams);
		
	}
}

