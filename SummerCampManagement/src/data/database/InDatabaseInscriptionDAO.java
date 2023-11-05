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
import business.entities.Inscription;
import business.entities.Monitor;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.repository.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.values.EducativeLevel;
import business.values.TimeSlot;
import data.database.SQLCriteria.AssistantInActivityCriteria;
import data.database.SQLCriteria.MonitorInActivityCriteria;


/**
 * La clase InDatabaseInscriptionDAO es una implementación en base de datos de un DAO de inscripciones.

 */

public class InDatabaseInscriptionDAO implements IDAO<Inscription, String> {
	private DBConnection dbConnection;
	
	/**
     * Constructor de la clase InDatabaseInscriptionDA0.
     */

    public InDatabaseInscriptionDAO() {
       this.dbConnection = DBConnection.getInstance();
    }


    /**
     * Busca una inscripción por su nombre.
     *
     * @param identifier El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el DAO.
     */

    @Override
    public Inscription find(String identifier) {
    	String[] parts = identifier.split("-");
    	int assistantId = Integer.parseInt(parts[0]);
    	int campId = Integer.parseInt(parts[1]);
    	
    	Inscription inscription;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_INSCRIPTION_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, assistantId);
			stmt.setInt(2, campId);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			rs.next();

			Date inscriptionDate = rs.getDate("inscriptionDate");
			float price = rs.getFloat("price");
			Boolean canBeCanceled = rs.getBoolean("canBeCanceled");
			
			inscription = new Inscription(
					assistantId,
					campId,
					inscriptionDate,
					price,
					canBeCanceled
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
		
		return inscription;
    }


    /**
     * Guarda una inscripción en el DAO.
     *
     * @param obj La inscripción a guardar en el DAO.
     */

    @Override
    public void save(Inscription obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_INSCRIPTION_QUERY")
			);
    		
    		ps.setInt(1, obj.getAssistantId());
    		ps.setInt(2, obj.getCampId());
    		ps.setDate(3, new Date(obj.getInscriptionDate().getTime()));
    		ps.setFloat(4, obj.getPrice());
    		ps.setBoolean(5, obj.canBeCanceled());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		System.out.println(e);
		}
    }


    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el DAO.
     *
     * @return Una lista de inscripciones.
     */

    @Override
    public List<Inscription> getAll(Optional<ICriteria> criteria) {
    	ArrayList<Inscription> listOfInscriptions = new ArrayList<Inscription>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_INSCRIPTIONS_QUERY");
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				query = criteriaObj.applyCriteria(query);
			}
			
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int assistantId = rs.getInt("assistantId");
				int campId = rs.getInt("campId");
				Date inscriptionDate = rs.getDate("inscriptionDate");
				float price = rs.getFloat("price");
				Boolean canBeCanceled = rs.getBoolean("canBeCanceled");
				listOfInscriptions.add(new Inscription(
						assistantId,
						campId,
						inscriptionDate,
						price,
						canBeCanceled
				));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			throw new DAOTimeoutException();
		}
		return listOfInscriptions;
    }

    /**
     * Elimina una inscripción del DAO.
     *
     * @param obj La inscripción a eliminar del DAO.
     */ 

    @Override
    public void delete(Inscription obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_INSCRIPTION_QUERY"));
	    	
	    	ps.setInt(1, obj.getAssistantId());
	    	ps.setInt(2, obj.getCampId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }
}
