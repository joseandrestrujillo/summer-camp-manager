package data.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import business.entities.Assistant;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.IDAO;


/**
 * La clase InDatabaseAssistantDAO es una implementación en base de datos de un DAO de asistentes.
 
 */
public class InDatabaseAssistantDAO implements IDAO<Assistant, Integer>{

	private DBConnection dbConnection;
    /**
     * Constructor de la clase InDatabaseAssistantDAO.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria y carga los asistentes almacenados en el DAO
     * 
     * @param dbUrl url de la base de datos
     * @param username username para acceder a la base de datos
     * @param password contrasena para acceder a la base de datos
     */
    public InDatabaseAssistantDAO() {
    	this.dbConnection = new DBConnection();
    }
    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el DAO.
     */
    @Override
    public Assistant find(Integer identifier) {
    	Assistant assistant;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = "select * from Assistant where id=?";
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			
			int id = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			Date birthDate = rs.getDate("birthDate");
			Boolean requiredSpecialAttention = rs.getBoolean("requireSpecialAttention");
			assistant = new Assistant(
					id,
					firstName,
					lastName,
					birthDate,
					requiredSpecialAttention);

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new AssistantNotFoundException();
		}
		return assistant;    }
    /**
     * Guarda a un asistente en el DAO.
     *
     * @param obj El asistente a guardar en el DAO.
     */
    @Override
    public void save(Assistant obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				"replace into Assistant (id,firstName,lastName,birthDate,requireSpecialAttention) values(?,?,?,?)"
			);
    		
    		ps.setInt(1, obj.getId());
    		ps.setString(2, obj.getFirstName());
    		ps.setString(3, obj.getLastName());
    		ps.setDate(4, (Date) obj.getBirthDate());
    		ps.setBoolean(5, obj.isRequireSpecialAttention());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		throw new DAOTimeoutException();
		}
    }
    /**
     * Obtiene una lista de todos los asistentes almacenados en el DAO.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<Assistant> getAll() {
    	ArrayList<Assistant> listOfAssistants = new ArrayList<Assistant>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = "select * from Assistant";
			
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				Date birthDate = rs.getDate("birthDate");
				Boolean requiredSpecialAttention = rs.getBoolean("requireSpecialAttention");
				listOfAssistants.add(new Assistant(
						id,
						firstName,
						lastName,
						birthDate,
						requiredSpecialAttention));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			throw new DAOTimeoutException();
		}
		return listOfAssistants;
    }
    /**
     * Elimina a un asistente del DAO.
     *
     * @param obj El asistente a eliminar del DAO.
     */
    @Override
    public void delete(Assistant obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement("delete from Assistant where id=?");
	    	
	    	ps.setInt(1, obj.getId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new AssistantNotFoundException();
		}
    }

}