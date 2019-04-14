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
import sql.classes.SQLBase;
import sql.classes.SQLServerBase;

public class FaceBikeSQL {
	
	private static Connection conn = null;
	private static String inputFile = "facebikeConfiguration.config";
	InputStream in;
	private static String database = null;
	private static String table = null;
	private static String sqlSelectSuffix = null;
	
	public FaceBikeSQL() throws Exception
	{
		try
		  {
	  
				//Grab the connection information for facebike
				Properties faceBikeConfig = new Properties();
				in = getClass().getClassLoader().getResourceAsStream(inputFile);
			
				faceBikeConfig.load(in);
				
				//We will default to jdbc although realistically this would be a config
				ConnectionType cType = ConnectionType.jdbc;
				
				String dbURL = faceBikeConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.connectionUrl));
				String dbUserName = faceBikeConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.username));
				String dbUserPassword = faceBikeConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.password));
				
				database = faceBikeConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.database));
				table = faceBikeConfig.getProperty(ConfigurationValues.GetConfigurationString(cType, ConfigValues.table));
				
				//Create full database url by appending the database
				String fullConnectionString = dbURL + ";databaseName=" + database;
				String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				conn = SQLBase.ConnectToDatabase(className, fullConnectionString, dbUserName, dbUserPassword);

				System.out.println("Connected to FaceBike");
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
	
	public static ArrayList<FaceBike> CreateArray(ResultSet rs) throws Exception
	  {
			ArrayList<FaceBike> list = new ArrayList<FaceBike>();
			
			while(rs.next())
			{
				FaceBike data = new FaceBike();
				
				data.setName(rs.getString("Full Name"));
				data.setEmail(rs.getString("Email"));
				data.setId(rs.getInt("Id"));
				data.setDepartment(rs.getString("Department"));
				data.setDateOfHire(rs.getString("DateofHire"));
				data.setCountry(rs.getString("Country"));
				data.setSalary(rs.getInt("Salary"));
				
				list.add(data);
			}
			
			return list;
	  }
	
 	public static ArrayList<FaceBike> ReturnTopTen() throws Exception
	  {
		  try
		  {
			  return CreateArray(SQLServerBase.ReturnTopX(conn, 10, table));
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<FaceBike> FindByName(String nameToFind) throws Exception
	  {
		  try
		  {
			  return CreateArray(SQLServerBase.FindByX(conn, table, "Full Name", nameToFind, false));

		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<FaceBike> FindByID(int id) throws Exception
	  {
		  try
		  {
			  return CreateArray(SQLServerBase.FindByX(conn, table, "Id", id, false));
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<FaceBike> FindByEmail(String email) throws Exception
	  {
		  try
		  {
			  return CreateArray(SQLServerBase.FindByX(conn, table, "Email", email, false));
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public static ArrayList<FaceBike> FindByDepartment(String department) throws Exception
	  {
		  try
		  {
			  return CreateArray(SQLServerBase.FindByX(conn, table, "Department", department, false));
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	
 	
 	public static void UpdateName(String fullName, int id) throws Exception
 	{
		   String sql = "UPDATE " + table + " SET \"Full Name\" = ? WHERE Id = ?";
		   
		   PreparedStatement pst = conn.prepareStatement(sql);
 		
 		try
 		{
 		   pst.setString(1, fullName);
 		   pst.setInt(2, id);

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

 	public static void UpdateDepartment(String department, int id) throws Exception
 	{
		   String sql = "UPDATE " + table + " SET Department = ? WHERE Id = ?";
		   
		   PreparedStatement pst = conn.prepareStatement(sql);
 		
 		try
 		{
 		   pst.setString(1, department);
 		   pst.setInt(2, id);

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

 	
 	public static void InsertItem(FaceBike entry) throws Exception
 	{
 		String sql = "INSERT INTO " + database + " ("
 	           + "([Salary],"
 	           + "[Full Name],"
 	           + "[Country],"
 	           +"[Created At],"
 	           +"[Department],"
 	           + "[Email]),"
 	           + "[DateofHire]),"
 	           + "VALUES(?,?,?,?,?,?,?)";
 		
 		PreparedStatement pst = conn.prepareStatement(sql);
 		
 		try
 		{
 		   pst.setDouble(1, entry.getSalary());
 		   pst.setString(2, entry.getName());
 		   pst.setString(3,  entry.getCountry());
 		   pst.setString(4,  entry.getCreatedAt());
 		   pst.setString(5, entry.getDepartment());
 		   pst.setString(6, entry.getEmail());
 		   pst.setString(7, entry.getDateOfHire());

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
