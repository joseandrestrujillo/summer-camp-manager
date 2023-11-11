package data.database.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import business.interfaces.ICriteria;
import data.database.DBManager;

/**
 * Esta clase implementa la interfaz ICriteria y representa un criterio que se aplica a los campamentos relacionados con una actividad.
 */
public class CampsRelatedWithAnActivityCriteria implements ICriteria{
    private String activityName;

    /**
     * Este es el constructor de la clase CampsRelatedWithAnActivityCriteria.
     * @param activityName El nombre de la actividad para la cual se aplicará el criterio.
     */
    public CampsRelatedWithAnActivityCriteria(String activityName) {
        this.activityName = activityName;
    }

    /**
     * Este método se utiliza para aplicar el criterio a un objeto PreparedStatement.
     * @param obj El PreparedStatement al que se aplicará el criterio.
     * @return T El PreparedStatement después de aplicarle el criterio.
     */
    @Override
    public <T> T applyCriteria(T obj) {
        DBManager dbConnection = DBManager.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = (Connection) ((PreparedStatement) obj).getConnection();
            String query = dbConnection.getQuery("GET_CAMPS_RELATED_WITH_AN_ACTIVITY_QUERY");
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
