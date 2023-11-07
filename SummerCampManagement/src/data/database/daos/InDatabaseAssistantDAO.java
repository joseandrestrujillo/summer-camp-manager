package data.database.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import business.dtos.AssistantDTO;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import data.database.DBManager;


/**
 * La clase InDatabaseAssistantDAO es una implementación en base de datos de un DAO de asistentes.
 
 */
public class InDatabaseAssistantDAO implements IDAO<AssistantDTO, Integer>{

	private DBManager dbConnection;
    /**
     * Constructor de la clase InDatabaseAssistantDAO.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria y carga los asistentes almacenados en el DAO
     * 
     * @param dbUrl url de la base de datos
     * @param username username para acceder a la base de datos
     * @param password contrasena para acceder a la base de datos
     */
    public InDatabaseAssistantDAO() {
    	this.dbConnection = DBManager.getInstance();
    }
    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el DAO.
     */
    @Override
    public AssistantDTO find(Integer identifier) {
    	AssistantDTO assistant;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_ASSISTANT_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			
			rs.next();
			int id = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			java.util.Date birthDate = new java.util.Date(rs.getDate("birthDate").getTime());
			Boolean requiredSpecialAttention = rs.getBoolean("requireSpecialAttention");
			assistant = new AssistantDTO(
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
			throw new NotFoundException();
		}
		return assistant;    
	}
    /**
     * Guarda a un asistente en el DAO.
     *
     * @param obj El asistente a guardar en el DAO.
     */
    @Override
    public void save(AssistantDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_ASSISTANT_QUERY")
			);
    		
    		ps.setInt(1, obj.getId());
    		ps.setString(2, obj.getFirstName());
    		ps.setString(3, obj.getLastName());
    		ps.setDate(4, new Date(obj.getBirthDate().getTime()));
    		ps.setBoolean(5, obj.isRequireSpecialAttention());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		System.out.println(e);
		}
    }
    /**
     * Obtiene una lista de todos los asistentes almacenados en el DAO.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<AssistantDTO> getAll(Optional<ICriteria> criteria) {
    	ArrayList<AssistantDTO> listOfAssistants = new ArrayList<AssistantDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_ASSISTANTS_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				stmt = criteriaObj.applyCriteria(stmt);
			} 
			
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				Date birthDate = rs.getDate("birthDate");
				Boolean requiredSpecialAttention = rs.getBoolean("requireSpecialAttention");
				listOfAssistants.add(new AssistantDTO(
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
		} catch (SQLException e){
			System.out.println(e);
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
    public void delete(AssistantDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_ASSISTANT_QUERY"));
	    	
	    	ps.setInt(1, obj.getId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }

}