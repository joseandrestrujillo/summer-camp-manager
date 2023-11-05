package data.database.sqlcriteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import business.interfaces.ICriteria;
import data.database.DBConnection;

public class MonitorInActivityCriteria implements ICriteria{
	private String activityName;
	public MonitorInActivityCriteria(String activityName) {
		 this.activityName=activityName;
	}
	@Override
	public <T> T applyCriteria(T obj) {
		DBConnection dbConnection = DBConnection.getInstance();
		PreparedStatement stmt = null;
		try {
			Connection con = (Connection) ((PreparedStatement) obj).getConnection();
			
			String query = dbConnection.getQuery("GET_MONITORS_OF_AN_ACTIVITY_QUERY");
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, activityName);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unchecked")
		T criteriaApplied =  (T) stmt;
		
		return criteriaApplied;
	}

}