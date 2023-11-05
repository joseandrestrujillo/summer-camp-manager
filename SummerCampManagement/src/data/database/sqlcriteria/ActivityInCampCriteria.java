package data.database.sqlcriteria;

import java.sql.PreparedStatement;

import business.interfaces.ICriteria;
import data.database.DBConnection;

public class ActivityInCampCriteria implements ICriteria{
	private int campID;
	public ActivityInCampCriteria(int campID) {
		 this.campID=campID;
	 }
	@Override
	public <T> T applyCriteria(T obj) {
		DBConnection dbConnection = DBConnection.getInstance();
		PreparedStatement stmt = con.prepareStatement(query);
		@SuppressWarnings("unchecked")
		T criteriaApplied =  (T) dbConnection.getQuery("GET_ACTIVITIES_OF_A_CAMP_QUERY");
		
		return criteriaApplied;
	}

}
 