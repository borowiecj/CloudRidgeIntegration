package integration.classes;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

    static private FileHandler fileTxt;
   
	
	public MyLogger() throws IOException
	{
	    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.FINE);
		fileTxt = new FileHandler("C:\\temp\\Logging.txt");
		logger.addHandler(fileTxt);
	}

}
