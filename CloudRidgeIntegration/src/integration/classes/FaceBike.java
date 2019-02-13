package integration.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import java.util.logging.Logger;
import integration.classes.MyLogger;



import integration.classes.ConfigurationValues.ConfigValues;
import integration.classes.ConfigurationValues.ConnectionType;

public class FaceBike {
	
	private Connection conn = null;
	private String fileInputFile = "facebikeConfiguration.config";
	InputStream in;
	private String table = "sample_data";
	private String sqlSelectSuffix = "Select * FROM [employees].[dbo].[" + table + "]";
	
  public FaceBike() throws IOException
  {
	  try
	  {
	  
			//Grab the connection information for facebike
			Properties faceBikeConfig = new Properties();
			in = getClass().getClassLoader().getResourceAsStream(fileInputFile);
		
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
    
  public ResultSet ReturnTopTen() throws Exception
  {
	  try
	  {
		   String sql = sqlSelectSuffix.replace("* FROM", "top 10 * FROM");
		   
		   PreparedStatement pst = conn.prepareStatement(sql);
		   
		   ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString("Full Name"));
			}
			
			return rs;

	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
		  throw ex;
	  }
  }
  
  public ResultSet FindUserByName(String name) throws Exception
  {
	  try
	  {
		   String sql = sqlSelectSuffix + "Where \"Full Name\" like ? ";
		   PreparedStatement pst = conn.prepareStatement(sql);
		   pst.setString(1, name);
		   
		   ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString("Full Name"));
			}
			
			return rs;
	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
		  throw ex;
	  }
  }
}
