package sql.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import integration.classes.FaceBike;

public class MySQLServerBase 
{
	private static String selectStatement = "Select * FROM @@TABLE@@ ";
	
	public static String SelectStatementWithUpdatedTable(String tableToQuery)
	{
		return selectStatement.replace("@@TABLE@@", tableToQuery);
	}
	
	public static String SelectStatementWithWhere(String tableToQuery, String columnName, boolean exactMatch)
	{
		   String sql = SelectStatementWithUpdatedTable(tableToQuery);
		   
		   if(exactMatch)
		   {
			   sql = sql + "Where " + columnName + " = ? ";
		   }
		   else
		   {
			   sql = sql + "Where " + columnName + " like ? ";
		   }
		   
		   return sql;
	}
	
  public static ResultSet ReturnTopX(Connection conn, int RowsReturned, String tableToQuery) throws Exception
  {
	  try
	  {
		   String sql = SelectStatementWithUpdatedTable(tableToQuery) + "LIMIT " + Integer.toString(RowsReturned);
					   ;
		   System.out.println("Sql Query: " + sql);

		   PreparedStatement pst = conn.prepareStatement(sql);
		   
		   ResultSet rs = pst.executeQuery();
		   
		   return rs;
			
	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
		  throw ex;
	  }
  }

  public static ResultSet FindByX(Connection conn, String tableToQuery, String columnName, String columnValue, boolean exactMatch) throws Exception
  {
	  try
	  {
		   String sql = SelectStatementWithWhere(tableToQuery, columnName, exactMatch);
		   System.out.println("Sql Query: " + sql);
		   
		   PreparedStatement pst = conn.prepareStatement(sql);
		   
		   if(exactMatch)
		   {
			   pst.setString(1, columnValue);
		   }
		   else
		   {
			   pst.setString(1, "%" + columnValue + "%");
		   }

		   return pst.executeQuery();
	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
		  throw ex;
	  }
  }

  public static ResultSet FindByX(Connection conn, String tableToQuery, String columnName, int columnValue, boolean exactMatch) throws Exception
  {
	  try
	  {
		   String sql = SelectStatementWithWhere(tableToQuery, columnName, exactMatch);
		   System.out.println("Sql Query: " + sql);
		   
		   PreparedStatement pst = conn.prepareStatement(sql);
		   
		   //We do not support wildcard matching with int so treat as exactmatch regardless of bool value
		   pst.setInt(1, columnValue);

		   return pst.executeQuery();
	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
		  throw ex;
	  }
  }

}
