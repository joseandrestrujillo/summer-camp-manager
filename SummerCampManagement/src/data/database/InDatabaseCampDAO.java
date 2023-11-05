package data.database;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.entities.Activity;
import business.entities.Assistant;
import business.entities.Camp;
import business.entities.Inscription;
import business.entities.Monitor;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import data.database.SQLCriteria.ActivityInCampCriteria;
import data.database.SQLCriteria.AssistantInActivityCriteria;
import data.database.SQLCriteria.InscriptionOfACampCriteria;
import data.database.SQLCriteria.MonitorInActivityCriteria;
/**
 * La clase InDatabaseCampDAO es una implementación en base de datos de un DAO de campamentos.
 
 */
public class InDatabaseCampDAO implements IDAO<Camp, Integer> {
	private DBConnection dbConnection;
	/**
     * Constructor de la clase InDatabaseCampDAO.
     * @param filePath La ruta de la tabla
     */

    public InDatabaseCampDAO() {
    	this.dbConnection = DBConnection.getInstance();
    }
     /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el DAO.
     */

    @Override
    public Camp find(Integer identifier) {
    	Camp camp;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_CAMP_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			
			int campID = rs.getInt("campID");
			Date start = rs.getDate("start");
			Date end = rs.getDate("end");
			String educativeLevelString = rs.getString("educativeLevel");
			EducativeLevel educativeLevel =  educativeLevelString == "PRESCHOOL"
											? EducativeLevel.PRESCHOOL
											: educativeLevelString == "ELEMENTARY"
												? EducativeLevel.ELEMENTARY
												: EducativeLevel.TEENAGER;
			int capacity = rs.getInt("capacity");
			int principalMonitorId = rs.getInt("principalMonitorId");
			int specialMonitorId = rs.getInt("specialMonitorId");
			
			camp = new Camp(
					campID,
					start,
					end,
					educativeLevel,
					capacity
			);
			
			InDatabaseMonitorDAO monitorDao = new InDatabaseMonitorDAO();
			
			camp.setPrincipalMonitor(monitorDao.find(principalMonitorId));
			camp.setSpecialMonitor(monitorDao.find(specialMonitorId));

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		
		InDatabaseActivityDAO activityDAO = new InDatabaseActivityDAO();
		
		List<Activity> activities = activityDAO.getAll(Optional.of(new ActivityInCampCriteria()));
		
		camp.setActivities(activities);
		
		
		
		return camp;
    }

    /**
     * Guarda un campamento en el DAO.
     *
     * @param obj El campamento a guardar en el DAO.
     */

    @Override
    public void save(Camp obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_CAMP_QUERY")
			);
    		
    		ps.setInt(1, obj.getCampID());
    		ps.setDate(2, new Date(obj.getStart().getTime()));
    		ps.setDate(3, new Date(obj.getEnd().getTime()));
    		ps.setString(4, obj.getEducativeLevel().name());
    		ps.setInt(5, obj.getCapacity());
    		ps.setInt(6, obj.getPrincipalMonitor() != null ? obj.getPrincipalMonitor().getId() : null);
    		ps.setInt(7, obj.getSpecialMonitor() != null ? obj.getSpecialMonitor().getId() : null);
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		System.out.println(e);
		}
    	
		InDatabaseActivityDAO activityDAO = new InDatabaseActivityDAO();
    	for (Activity activity : obj.getActivities()) {
    		activityDAO.save(activity);
    		try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("UPDATE_ACTIVITY_CAMP_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setString(2, activity.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		System.out.println(e);
			}
		}
    }
    /**
     * Obtiene una lista de todos los campamentos almacenados en el DAO.
     *
     * @return Una lista de campamentos.
     */

    @Override
    public List<Camp> getAll(Optional<ICriteria> criteria) {
    	ArrayList<Camp> listOfCamps = new ArrayList<Camp>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_CAMP_IDS_QUERY");
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				query = criteriaObj.applyCriteria(query);
			}
			
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int campID = rs.getInt("campID");
				listOfCamps.add(find(campID));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			throw new DAOTimeoutException();
		}
		return listOfCamps;
    }
        /**
     * Elimina un campamento del DAO.
     *
     * @param obj El campamento a eliminar del DAO.
     */

    @Override
    public void delete(Camp obj) {
    	InDatabaseActivityDAO activityDAO = new InDatabaseActivityDAO();
    	for (Activity activity : obj.getActivities()) {
    		activityDAO.save(activity);
    		try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("DELETE_ACTIVITY_CAMP_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setString(2, activity.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		System.out.println(e);
			}
		}
    	
    	InDatabaseInscriptionDAO inscriptionDAO = new InDatabaseInscriptionDAO();
    	List<Inscription> inscriptions = inscriptionDAO.getAll(Optional.of(new InscriptionOfACampCriteria()));
    	for (Inscription inscription : inscriptions) {
    		inscriptionDAO.delete(inscription);
    	}
    	
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_CAMP_QUERY"));
	    	
	    	ps.setInt(1, obj.getCampID());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }

}
