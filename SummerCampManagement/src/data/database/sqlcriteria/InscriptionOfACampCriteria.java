package data.database.sqlcriteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import business.interfaces.ICriteria;
import data.database.DBConnection;

public class InscriptionOfACampCriteria implements ICriteria {
	private int campID;
	public InscriptionOfACampCriteria(int campID) {
		 this.campID=campID;
	}
	@Override
	public <T> T applyCriteria(T obj) {
		DBConnection dbConnection = DBConnection.getInstance();
		PreparedStatement stmt = null;
		try {
			Connection con = (Connection) ((PreparedStatement) obj).getConnection();
			
			String query = dbConnection.getQuery("GET_INSCRIPTIONS_OF_A_CAMP_QUERY");
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
