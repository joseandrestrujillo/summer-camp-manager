package data.database.daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import data.database.DBManager;
import data.database.sqlcriteria.AssistantInActivityCriteria;
import data.database.sqlcriteria.CampsRelatedWithAnActivityCriteria;
import data.database.sqlcriteria.MonitorInActivityCriteria;

/**
 * La clase InDatabaseActivityDAO es una implementación en base de datos de un DAO de actividades.
 */
public class InDatabaseActivityDAO implements IDAO<ActivityDTO, String>{
	private DBManager dbConnection;
    /**
     * Constructor de la clase InDatabaseActivityDAO.
     * Inicializa un nuevo mapa para almacenar actividades en memoria y carga las actividades almacenadas en la base de datos
     * 
     * @param filePath path a la tabla de actividades
     */
    public InDatabaseActivityDAO() {
    	this.dbConnection = DBManager.getInstance();
    }
    
    /**
     * Busca una actividad por su nombre.
     *
     * @param identifier El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el DAO.
     */
    @Override
    public ActivityDTO find(String identifier) {

    	ActivityDTO activity;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_ACTIVITY_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			rs.next();

			String activityName = rs.getString("activityName");
			String educativeLevelString = rs.getString("educativeLevel");
			EducativeLevel educativeLevel = EducativeLevel.valueOf(educativeLevelString);
			String timeSlotString = rs.getString("timeSlot");
			TimeSlot timeSlot =  TimeSlot.valueOf(timeSlotString);
			int maxAssistants = rs.getInt("maxAssistants");
			int neededMonitors = rs.getInt("neededMonitors");
			
			activity = new ActivityDTO(
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
		
		List<AssistantDTO> assistants = assistantDao.getAll(Optional.of(new AssistantInActivityCriteria(identifier)));
		
		activity.setAssistants(assistants);
		
		InDatabaseMonitorDAO monitorDao = new InDatabaseMonitorDAO();
		
		List<MonitorDTO> monitors = monitorDao.getAll(Optional.of(new MonitorInActivityCriteria(identifier)));
		
		activity.setMonitorList(monitors);
		
		
		return activity;       
	}
    
    /**
     * Guarda una actividad en el DAO.
     *
     * @param obj La actividad a guardar en el DAO.
     */
    @Override
    public void save(ActivityDTO obj) {
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
    	for (AssistantDTO assistant : obj.getAssistants()) {
			assistantDAO.save(assistant);
		}
    	
    	InDatabaseMonitorDAO monitorDAO = new InDatabaseMonitorDAO();
    	for (MonitorDTO monitor : obj.getMonitorList()) {
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
    public List<ActivityDTO> getAll(Optional<ICriteria> criteria) {
    	ArrayList<ActivityDTO> listOfActivitiess = new ArrayList<ActivityDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_ACTIVITY_NAMES_QUERY");
			PreparedStatement stmt = con.prepareStatement(query);
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				stmt = criteriaObj.applyCriteria(stmt);
			}
			
			
			ResultSet rs = (ResultSet) stmt.executeQuery();
			

			while (rs.next()) {
				String activityName = rs.getString("activityName");
				listOfActivitiess.add(find(activityName));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
			
		} catch (SQLException e){
			System.out.println(e);
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
    public void delete(ActivityDTO obj) {
    	InDatabaseCampDAO campDAO = new InDatabaseCampDAO();
    	List<CampDTO> camps = campDAO.getAll(Optional.of(new CampsRelatedWithAnActivityCriteria(obj.getActivityName())));
    	
    	for (CampDTO camp : camps) {
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
    	
    	for (MonitorDTO monitor : obj.getMonitorList()) {
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