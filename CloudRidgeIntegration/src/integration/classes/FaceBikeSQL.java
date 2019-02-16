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

public class FaceBikeSQL {
	
	private Connection conn = null;
	private String inputFile = "facebikeConfiguration.config";
	InputStream in;
	private String table = "sample_data";
	private String sqlSelectSuffix = "Select * FROM [employees].[dbo].[" + table + "]";
	
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

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				this.conn = DriverManager.getConnection(dbURL, dbUserName, dbUserPassword);
				if (this.conn != null) {
				    System.out.println("Connected");
				}
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
	
	  public ArrayList<FaceBike> CreateArray(ResultSet rs) throws Exception
	  {
			ArrayList<FaceBike> list = new ArrayList<FaceBike>();
			
			while(rs.next())
			{
				FaceBike data = new FaceBike();
				
				String name = rs.getString("Full Name");
				String email = rs.getString("Email");
				int id = rs.getInt("Id");

				data.setName(name);
				data.setEmail(email);
				data.setId(id);
				data.setDepartment(rs.getString("Department"));
				
				list.add(data);
			}
			
			return list;
	  }
	
 	public ArrayList<FaceBike> ReturnTopTen() throws Exception
	  {
		  try
		  {
			   String sql = sqlSelectSuffix.replace("* FROM", "top 10 * FROM");
			   
			   PreparedStatement pst = conn.prepareStatement(sql);
			   
			   ResultSet rs = pst.executeQuery();
			   
			   return CreateArray(rs);
				
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public ArrayList<FaceBike> FindByName(String nameToFind) throws Exception
	  {
		  try
		  {
			   String sql = sqlSelectSuffix + "Where \"Full Name\" like ?";
			   
			   PreparedStatement pst = conn.prepareStatement(sql);
			   pst.setString(1, "%" + nameToFind + "%");
			   
			   ResultSet rs = pst.executeQuery();
				
			   return CreateArray(rs);
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public ArrayList<FaceBike> FindByID(int id) throws Exception
	  {
		  try
		  {
  
			   String sql = sqlSelectSuffix + "Where Id = ?";
			   
			   PreparedStatement pst = conn.prepareStatement(sql);
			   pst.setInt(1, id);
			   
			   ResultSet rs = pst.executeQuery();
				
			   return CreateArray(rs);
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public ArrayList<FaceBike> FindByEmail(String email) throws Exception
	  {
		  try
		  {
			   String sql = sqlSelectSuffix + "Where Email = ?";
			   
			   PreparedStatement pst = conn.prepareStatement(sql);
			   pst.setString(1, email);
			   
			   ResultSet rs = pst.executeQuery();
				
			   return CreateArray(rs);
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public ArrayList<FaceBike> FindByDepartment(String department) throws Exception
	  {
		  try
		  {
			  String sql = sqlSelectSuffix + "Where Department like ?";
			   
			   PreparedStatement pst = conn.prepareStatement(sql);
			   pst.setString(1, "%" + department + "%");
			   
			   ResultSet rs = pst.executeQuery();
				
			   return CreateArray(rs);
	
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

 	public void UpdateName(String fullName, int id) throws Exception
 	{
		   String sql = "UPDATE sample_data SET Full Name = ? WHERE Id = id";
		   
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

 	public void InsertItem(FaceBike entry) throws Exception
 	{
 		String sql = "INSERT INTO sample_data ("
 	           + "([Salary],"
 	           + "[Full Name],"
 	           + "[Country],"
 	           +"[Created At],"
 	           +"[Department],"
 	           + "[Email])"
 	           + "VALUES(?,?,?,?,?,?)";
 		
 		PreparedStatement pst = conn.prepareStatement(sql);
 		
 		try
 		{
 		   pst.setDouble(1, entry.getSalary());
 		   pst.setString(2, entry.getName());
 		   pst.setString(3,  entry.getCountry());
 		   pst.setString(4,  entry.getCreatedAt());
 		   pst.setString(5, entry.getDepartment());
 		   pst.setString(6, entry.getEmail());

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
