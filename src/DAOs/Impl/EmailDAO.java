package DAOs.Impl;

import DAOs.GenericDAO;
import Entities.Email;
import Entities.EmailTypes;
import Exceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//implemento la interfaz de GEnericDao para la entidad Email.
//La estructura de todos los DAOs es muy parecida entre si, lo que permite legibilidad, entendimiento y facil matenimiento
public class EmailDAO extends GenericDAOImpl<Email> {

    public void create(String query) throws DAOException {
        try {
            super.create(query);
        }catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
    }

    public void delete(String query) throws DAOException {
        try {
            super.delete(query);
        }
        catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
    }

    public void update(String query) throws DAOException {
        try {
            super.update(query);
        }
        catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
    }


    //ACA ESTA mapEntity() Toma un ResultSet y busca los campos necesarios
    //EmailDAO.mapEntity() y UsersDAO.mapEntity() van a ser SIEMPRE disitntos, ya que T es distinto
    @Override
    public Email mapEntity(ResultSet resultSet) throws SQLException {

        try
        {
            int id = resultSet.getInt("Id");
            int accountId = resultSet.getInt("AccountId");
            String from = resultSet.getString("FromUser");
            String to = resultSet.getString("To");
            String body = resultSet.getString("Body");
            String subject = resultSet.getString("Subject");
            String host = resultSet.getString("Host");
            EmailTypes type = EmailTypes.values()[resultSet.getInt("State")];

            return new Email(body, to, from, subject, host, type, accountId, id);
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Email get(String query) throws DAOException {
            return super.get(query);
    }

    public List<Email> getAll(String query) throws DAOException {
        try {
            return super.getAll(query);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch (CannotCloseConnection e ){
            throw new CannotCloseConnection(e.getMessage());
        } catch (RollbackException e) {
            throw new RollbackException(e.getMessage());
        }
    }

    public void tst() throws DAOException {
            super.tst();
    }
}
