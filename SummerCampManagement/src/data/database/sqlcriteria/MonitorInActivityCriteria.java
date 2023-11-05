package data.database.sqlcriteria;

import business.interfaces.ICriteria;
import data.database.DBConnection;

public class MonitorInActivityCriteria implements ICriteria{

	@Override
	public <T> T applyCriteria(T obj) {
		DBConnection dbConnection = DBConnection.getInstance();
	    
		@SuppressWarnings("unchecked")
		T criteriaApplied =  (T) dbConnection.getQuery("GET_MONITORS_OF_AN_ACTIVITY_QUERY");
		
		return criteriaApplied;
	}

}
