package DAOs.Impl;

import DAOs.GenericDAO;
import Entities.Contact;
import Entities.Email;
import Exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactsDAO extends GenericDAOImpl<Contact> implements GenericDAO<Contact> {

    @Override
    public void create(String query) throws DAOException {
        super.create(query);
    }

    @Override
    public List<Contact> getAll(String query) throws DAOException {
        return super.getAll(query);
    }

    @Override
    public void delete(String query) throws DAOException {
        super.delete(query);
    }

    @Override
    public Contact mapEntity(ResultSet resultSet) throws SQLException {
        try{
            int id = resultSet.getInt("Id");
            int u1 = resultSet.getInt("UserId1");
            int u2 = resultSet.getInt("UserId2");
            return new Contact(u1, u2);
        }
        catch (SQLException e){
            throw new SQLException(e);
        }
    }
}
