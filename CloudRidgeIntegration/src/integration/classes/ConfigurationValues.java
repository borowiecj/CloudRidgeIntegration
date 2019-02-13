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
		password
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
				
			default:
				throw new IllegalArgumentException("Undefined configuration value");
		}
		
		return connectionPrefix + "." + cValue;
	}

}