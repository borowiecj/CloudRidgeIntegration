package integration.classes;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TransferTo 
{
	  public Boolean ToFaceBike(WhosApp user) throws Exception
	  {
		  Boolean success = false;
		  
		  try
		  {
			  FaceBike fbUser = new FaceBike();
				fbUser.setName(user.getFullName());
				fbUser.setSalary(user.getSalary());
				fbUser.setDateOfHire(user.getHire_date());
				fbUser.setDepartment(user.getDepartment_name());
				
				//WE are setting conuntry as the state right now - we really should have a mapping
				fbUser.setCountry(user.getState());

				//We should really confirm the use does not exist prior and do a lot more prechecks
				FaceBikeSQL.InsertItem(fbUser);
				
				success= true;
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  success = false;
		  }


		  return success;
	  }
	  
	  public Boolean ToWhosApp(FaceBike user) throws Exception
	  {
		  Boolean success = false;
		  
		  try
		  {
			  WhosApp whosAppUser = new WhosApp();
			  whosAppUser.setFirst_name(user.getFirstName());
			  whosAppUser.setLast_name(user.getLastName());
			  whosAppUser.setHire_date(user.getDateOfHire());
			  whosAppUser.setDepartment_name(user.getDepartment());
				
				//WE are setting conuntry as the state right now - we really should have a mapping
			  	whosAppUser.setState(user.getCountry());
				//We should really confirm the use does not exist prior and do a lot more prechecks
			   	WhosAppSQL.InsertItem(whosAppUser);
			   	
				success= true;
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  success = false;
		  }


		  return success;
	  }

}

