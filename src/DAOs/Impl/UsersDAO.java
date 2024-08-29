package DAOs.Impl;

import DAOs.GenericDAO;
import Entities.Email;
import Entities.User;
import Exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO extends GenericDAOImpl<User> implements GenericDAO<User> {

    @Override
    public User mapEntity(ResultSet resultSet) throws SQLException {
        try{
            int id = resultSet.getInt("Id");
            String username = resultSet.getString("Username");
            String host = resultSet.getString("Host");
            String port = resultSet.getString("Port");
            String hostName = resultSet.getString("HostUserName");
            String hostPassword = resultSet.getString("HostPassword");
            String popHost =  resultSet.getString("PopHost");
            String popHostPort =  resultSet.getString("PopHostPort");

            User user =  new User(username, host, hostPassword, port, hostName, popHost, popHostPort, id);
            return user;
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void create(String query) throws DAOException {
        try {
            super.create(query);
        }catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
    }

    public void update(String query) throws DAOException {
        try {
            super.update(query);
        }catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
    }
}
