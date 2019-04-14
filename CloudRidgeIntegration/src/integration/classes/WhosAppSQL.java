package integration.classes;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import integration.classes.ConfigurationValues.ConfigValues;
import integration.classes.ConfigurationValues.ConnectionType;
import sql.classes.MySQLServerBase;
import sql.classes.SQLBase;
import sql.classes.SQLServerBase;

public class WhosAppSQL {
	
	private static Connection conn = null;
	private static String inputFile = "whosAppConfiguration.config";
	InputStream in;
	private static String database = null;
	private static String table = null;
	private static String sqlSelectSuffix = null;
	
	public WhosAppSQL() throws Exception
	{
		try
		  {
	  
				//Grab the connection information for facebike
				Properties whosAppConfig = new Properties();
				in = getClass().getClassLoader().getResourceAsStream(inputFile);
			
				whosAppConfig.load(in);
				
				//We will default to jdbc although realistically this would be a config
				ConnectionType cType = ConnectionType.jdbc;
				
				String dbURL = whosAppConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.connectionUrl));
				String dbUserName = whosAppConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.username));
				String dbUserPassword = whosAppConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.password));
				
				database = whosAppConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.database));
				table = whosAppConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.table));
				sqlSelectSuffix = "Select * FROM " + table + " " ;
				
				//Create full database url by appending the database
				String fullConnectionString = dbURL + "/" + database;
				
				String className = "com.mysql.cj.jdbc.Driver";
				this.conn = SQLBase.ConnectToDatabase(className, fullConnectionString, dbUserName, dbUserPassword);

				System.out.println("Connected to WhosApp");
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  in.close();
		  }
	}
	
	  public static ArrayList<WhosApp> CreateArray(ResultSet rs) throws Exception
	  {
			ArrayList<WhosApp> list = new ArrayList<WhosApp>();
			
			while(rs.next())
			{
				WhosApp data = new WhosApp();
				
				data.setEmployee_id(rs.getInt("employee_id"));
				data.setFirst_name(rs.getString("first_name"));
				data.setLast_name(rs.getString("last_name"));
				data.setHire_date(rs.getString("hire_date"));
				data.setDepartment_name(rs.getString("department_name"));
				data.setPosition_name(rs.getString("position_name"));
				data.setZip(rs.getInt("zip"));
				data.setEmploment_status(rs.getString("employment_status"));
				list.add(data);
			}
			
			return list;
	  }
	
 	public static ArrayList<WhosApp> ReturnTopTen() throws Exception
	  {
		  try
		  {
			  return CreateArray(MySQLServerBase.ReturnTopX(conn, 10, table));
			
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<WhosApp> FindByFirstName(String nameToFind) throws Exception
	  {
		  try
		  {
			  return CreateArray(MySQLServerBase.FindByX(conn, table, "first_name", nameToFind, false));
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }
 	
 	public static ArrayList<WhosApp> FindByLastName(String nameToFind) throws Exception
	  {
		  try
		  {
			  return CreateArray(MySQLServerBase.FindByX(conn, table, "last_name", nameToFind, false));
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<WhosApp> FindByID(int id) throws Exception
	  {
		  try
		  {
			  return CreateArray(MySQLServerBase.FindByX(conn, table, "employee_id", id, false));
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<WhosApp> FindByState(String state) throws Exception
	  {
		  try
		  {
			  return CreateArray(MySQLServerBase.FindByX(conn, table, "state", state, false));
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<WhosApp> FindByDepartment(String department) throws Exception
	  {
		  try
		  {
			  return CreateArray(MySQLServerBase.FindByX(conn, table, "department_name", department, true));
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }
	
 	public static void InsertItem(WhosApp entry) throws Exception
 	{
 		String sql =  "INSERT INTO " + database + "("
 		+ "state"
 		+ "zip,"
 		+ "employment_status,"
 		+ "position_name,"
 		+ "department_name,"
 		+ "salary,"
 		+ "first_name,"
 		+ "last_name,"
 		+ "hire_date)"
		+ "VALUES(?,?,?,?,?,?,?,?,?)";

 		PreparedStatement pst = conn.prepareStatement(sql);
 		
 		try
 		{

  		   pst.setString(1, entry.getState());
  		   pst.setInt(2, entry.getZip());
  		   pst.setString(3,  entry.getEmploment_status());
  		   pst.setString(4,  entry.getPosition_name());
  		   pst.setString(5, entry.getDepartment_name());
  		   pst.setInt(6, entry.getSalary());
  		   pst.setString(7, entry.getFirst_name());
  		   pst.setString(8, entry.getLast_name());
  		   pst.setString(9, entry.getHire_date());

 		   pst.executeUpdate();
 	    }
	    catch(Exception ex)
 		{
	    	ex.printStackTrace();
 		}
 		finally
 		{
 			pst.close();
 		}
 	}
}
