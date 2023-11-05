package data.database.sqlcriteria;

import business.interfaces.ICriteria;
import data.database.DBConnection;

public class ActivityInCampCriteria implements ICriteria{

	@Override
	public <T> T applyCriteria(T obj) {
		DBConnection dbConnection = DBConnection.getInstance();
	    
		@SuppressWarnings("unchecked")
		T criteriaApplied =  (T) dbConnection.getQuery("GET_ACTIVITIES_OF_A_CAMP_QUERY");
		
		return criteriaApplied;
	}

}
