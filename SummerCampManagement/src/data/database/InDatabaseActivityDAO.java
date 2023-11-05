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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.entities.Activity;
import business.entities.Assistant;
import business.entities.Camp;
import business.entities.Monitor;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import data.database.SQLCriteria.AssistantInActivityCriteria;
import data.database.SQLCriteria.CampsRelatedWithAnActivityCriteria;
import data.database.SQLCriteria.MonitorInActivityCriteria;

/**
 * La clase InDatabaseActivityDAO es una implementación en base de datos de un DAO de actividades.
 */
public class InDatabaseActivityDAO implements IDAO<Activity, String>{
	private DBConnection dbConnection;
    /**
     * Constructor de la clase InDatabaseActivityDAO.
     * Inicializa un nuevo mapa para almacenar actividades en memoria y carga las actividades almacenadas en la base de datos
     * 
     * @param filePath path a la tabla de actividades
     */
    public InDatabaseActivityDAO() {
    	this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Busca una actividad por su nombre.
     *
     * @param identifier El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el DAO.
     */
    @Override
    public Activity find(String identifier) {

    	Activity activity;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_ACTIVITY_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			rs.next();

			String activityName = rs.getString("activityName");
			String educativeLevelString = rs.getString("educativeLevel");
			EducativeLevel educativeLevel =  educativeLevelString == "PRESCHOOL"
											? EducativeLevel.PRESCHOOL
											: educativeLevelString == "ELEMENTARY"
												? EducativeLevel.ELEMENTARY
												: EducativeLevel.TEENAGER;
			String timeSlotString = rs.getString("timeSlot");
			TimeSlot timeSlot =  timeSlotString == "MORNING"
											? TimeSlot.MORNING
											: TimeSlot.AFTERNOON;
			int maxAssistants = rs.getInt("maxAssistants");
			int neededMonitors = rs.getInt("neededMonitors");
			
			activity = new Activity(
					activityName,
					educativeLevel,
					timeSlot,
					maxAssistants,
					neededMonitors
			);

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		
		InDatabaseAssistantDAO assistantDao = new InDatabaseAssistantDAO();
		
		List<Assistant> assistants = assistantDao.getAll(Optional.of(new AssistantInActivityCriteria()));
		
		activity.setAssistants(assistants);
		
		InDatabaseMonitorDAO monitorDao = new InDatabaseMonitorDAO();
		
		List<Monitor> monitors = monitorDao.getAll(Optional.of(new MonitorInActivityCriteria()));
		
		activity.setMonitorList(monitors);
		
		
		return activity;       
	}
    
    /**
     * Guarda una actividad en el DAO.
     *
     * @param obj La actividad a guardar en el DAO.
     */
    @Override
    public void save(Activity obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_ACTIVITY_QUERY")
			);
    		
    		ps.setString(1, obj.getActivityName());
    		ps.setString(2, obj.getEducativeLevel().name());
    		ps.setString(3, obj.getTimeSlot().name());
    		ps.setInt(4, obj.getMaxAssistants());
    		ps.setInt(5, obj.getNeededMonitors());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		System.out.println(e);
		}
    	
    	InDatabaseAssistantDAO assistantDAO = new InDatabaseAssistantDAO();
    	for (Assistant assistant : obj.getAssistants()) {
			assistantDAO.save(assistant);
		}
    	
    	InDatabaseMonitorDAO monitorDAO = new InDatabaseMonitorDAO();
    	for (Monitor monitor : obj.getMonitorList()) {
			monitorDAO.save(monitor);
			try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("UPDATE_MONITOR_ACTIVITY_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, monitor.getId());
	    		ps.setString(2, obj.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		System.out.println(e);
			}
		}
    }
    
    /**
     * Obtiene una lista de todas las actividades almacenadas en el DAO.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<Activity> getAll(Optional<ICriteria> criteria) {
    	ArrayList<Activity> listOfActivitiess = new ArrayList<Activity>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_ACTIVITY_NAMES_QUERY");
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				query = criteriaObj.applyCriteria(query);
			}
			
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				String activityName = rs.getString("activityName");
				listOfActivitiess.add(find(activityName));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			throw new DAOTimeoutException();
		}
		return listOfActivitiess;
    }

    /**
     * Elimina una actividad del DAO.
     *
     * @param obj La actividad a eliminar del DAO.
     */
    @Override
    public void delete(Activity obj) {
    	InDatabaseCampDAO campDAO = new InDatabaseCampDAO();
    	List<Camp> camps = campDAO.getAll(Optional.of(new CampsRelatedWithAnActivityCriteria()));
    	
    	for (Camp camp : camps) {
    		try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("DELETE_ACTIVITY_CAMP_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, camp.getCampID());
	    		ps.setString(2, obj.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		System.out.println(e);
			}
    	}
    	
    	for (Monitor monitor : obj.getMonitorList()) {
			try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("DELETE_MONITOR_ACTIVITY_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, monitor.getId());
	    		ps.setString(2, obj.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		System.out.println(e);
			}
		}
    	
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_ACTIVITY_QUERY"));
	    	
	    	ps.setString(1, obj.getActivityName());
	    	
	    	ps.executeUpdate();
	    	
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }

}