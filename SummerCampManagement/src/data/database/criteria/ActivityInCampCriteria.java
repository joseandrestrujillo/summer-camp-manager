package data.database.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

import business.interfaces.ICriteria;
import data.database.DBManager;

public class ActivityInCampCriteria implements ICriteria{
	private int campID;
	public ActivityInCampCriteria(int campID) {
		 this.campID=campID;
	}
	
	@Override
	public <T> T applyCriteria(T obj) {
		DBManager dbConnection = DBManager.getInstance();
		PreparedStatement stmt = null;
		try {
			Connection con = (Connection) ((PreparedStatement) obj).getConnection();
			
			String query = dbConnection.getQuery("GET_ACTIVITIES_OF_A_CAMP_QUERY");
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, campID);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unchecked")
		T criteriaApplied =  (T) stmt;
		
		return criteriaApplied;
	}

}
 