package integration.classes;

public class ConfigurationValues 
{
	public enum ConnectionType
	{
		jdbc
	}
	
	public enum ConfigValues
	{
		connectionUrl,
		username,
		password,
		table,
		database
	}
	
	public static String GetConfigurationString(ConnectionType cType, ConfigValues configValue) throws Exception
	{
		String connectionPrefix = cType.toString();
		String cValue = null;
		
		switch(configValue)
		{
			case connectionUrl :
				cValue =  "connectionUrl";
				break;
				
			case username :
				cValue = "username";
				break;
				
			case password :
				cValue = "password";
				break;
				
			case table :
				cValue = "table";
				break;
				
			case database :
				cValue = "database";
				break;
				
			default:
				throw new IllegalArgumentException("Undefined configuration value");
		}
		
		return connectionPrefix + "." + cValue;
	}

}