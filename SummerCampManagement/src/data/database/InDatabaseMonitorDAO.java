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

import business.entities.Assistant;
import business.entities.Monitor;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
/**
 * La clase InDatabaseMonitorDAO es una implementación en base de datos de un DAO de la clase monitor.
 
 */
public class InDatabaseMonitorDAO implements IDAO<Monitor, Integer>{
	private DBConnection dbConnection;
	/**
     * Constructor de la clase InDatabaseMonitorDAO.
     * Inicializa un nuevo mapa para almacenar monitores en memoria y recibe la ruta de la tabla como parametro
     */
    public InDatabaseMonitorDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el DAO.
     */
    @Override
    public Monitor find(Integer identifier) {
    	Monitor monitor;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_MONITOR_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			Boolean isSpecialEducator = rs.getBoolean("specialEducator");
			
			monitor = new Monitor(
					id,
					firstName,
					lastName,
					isSpecialEducator);

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		return monitor;
    }
    /**
     * Guarda un monitor en el DAO.
     *
     * @param obj El monitor a guardar en el  DAO.
     */
    @Override
    public void save(Monitor obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_MONITOR_QUERY")
			);
    		
    		ps.setInt(1, obj.getId());
    		ps.setString(2, obj.getFirstName());
    		ps.setString(3, obj.getLastName());
    		ps.setBoolean(5, obj.isSpecialEducator());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		System.out.println(e);
		}
    }
    /**
     * Obtiene una lista de todos los monitores almacenados en el DAO.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<Monitor> getAll(Optional<ICriteria> criteria) {
    	ArrayList<Monitor> listOfMonitors = new ArrayList<Monitor>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_MONITORS_QUERY");
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				query = criteriaObj.applyCriteria(query);
			}
			
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				Boolean isSpecialEducator = rs.getBoolean("specialEducator");
				listOfMonitors.add(new Monitor(
						id,
						firstName,
						lastName,
						isSpecialEducator));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			throw new DAOTimeoutException();
		}
		return listOfMonitors;
	}

    /**
     * Elimina un monitor del DAO.
     *
     * @param obj El monitor a eliminar del DAO.
     */
    @Override
    public void delete(Monitor obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_MONITOR_QUERY"));
	    	
	    	ps.setInt(1, obj.getId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }
}