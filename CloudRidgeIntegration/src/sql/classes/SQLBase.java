package sql.classes;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLBase {
	
	public static Connection ConnectToDatabase(String className, String connectionString, String userName, String password) throws Exception
	{
		try
		{
			Connection conn = null;
			Class.forName(className);
			conn = DriverManager.getConnection(connectionString, userName, password);
			if (conn != null) {
			    System.out.println("Connected to" + connectionString);
			    return conn;
			}
			else
			{
				throw new Exception("We failed to connect to: ....for some unknown reason" + connectionString);
			}
		}
		catch(Exception ex)
	    {
		  ex.printStackTrace();
		  throw ex;
	    }
	}


}