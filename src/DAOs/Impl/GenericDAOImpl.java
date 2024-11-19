package DAOs.Impl;

import DAOs.GenericDAO;
import Exceptions.*;
import Genetic.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAOImpl<T> implements GenericDAO<T>{

    @Override
    public void create(String query) throws DAOException {
        this.excecutePersistentChanges(query);
    }

    @Override
    public void delete(String query) throws DAOException {
        this.excecutePersistentChanges(query);
    }

    @Override
    public void update(String query) throws DAOException {
        this.excecutePersistentChanges(query);
    }

    //Cada DAO tiene su propio T, por ende cada uno debe mappear sus resultados de forma distinto
    public abstract T mapEntity(ResultSet resultSet) throws SQLException;


    /*
    Tanto get como getAll invocan al mismo metodo. La diferencia es la query y como manipulo los resultados. 
    */
    @Override
    public T get(String query) throws DAOException {
        T result = null;
        try {
            List<T> results = getList(query);
            if(results != null && results.size() > 0){
                result = results.get(0);
            }
        } catch (NotFoundException nfe) {
            throw new DAOException(nfe.getMessage());
        } catch (CannotCloseConnection ce) {
            throw new DAOException(ce.getMessage());
        } catch (RollbackException re) {
            throw new DAOException(re.getMessage());
        }
        return result;
    }

    @Override
    public List<T> getAll(String query) throws DAOException {
        return getList(query);
    }


        //tst es para ser invocada la primera vez que se levanta el programa para asi crear las tablas necesarias.
        //se llama tst porque estaba cansada JAJAJ
        @Override
        public void tst () throws DAOException {
            try {
                String emailsTable = "CREATE TABLE emails (Id INTEGER PRIMARY KEY AUTO_INCREMENT, To VARCHAR(50), FromUser VARCHAR(50), Subject VARCHAR(100), Body VARCHAR(255), Host VARCHAR(100), State INTEGER, AccountId INT not NULL)";
                String usersTable = "CREATE TABLE Users(Id INTEGER PRIMARY KEY AUTO_INCREMENT, UserName varchar(50), Password varchar(50))";
                String contactsTable = "CREATE TABLE Contacts (  UserId1 INT, UserId2 INT, foreign key (UserId1) references USERS(Id), foreign key (UserId2) references USERS(Id))";
                excecutePersistentChanges(emailsTable);
                excecutePersistentChanges(usersTable);
                excecutePersistentChanges(contactsTable);
            } catch (CannotSaveException cse) {
                throw new DAOException(cse.getMessage());
            } catch (CannotCloseConnection ce) {
                throw new DAOException(ce.getMessage());
            } catch (RollbackException re) {
                throw new DAOException(re.getMessage());
            }
        }

        //INSERT, UPDATE, DELETE. Toddos manejan la misma logica base, asi que tengo un solo metodo que ejecuta la query.
        private void excecutePersistentChanges (String query) throws CannotCloseConnection, RollbackException, CannotSaveException {
            Connection con = DBManager.connect();

            try {
                Statement s = con.createStatement();
                s.executeUpdate(query);
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                    throw new CannotSaveException("No se encontro el registro buscado");
                } catch (SQLException e1) {
                    throw new RollbackException("Se rompio todo (?");
                }
            } finally {
                try {
                    con.close();
                } catch (SQLException e1) {
                    throw new CannotCloseConnection("Hubo un problema al cortar la conexión");
                }
            }
        }

        private List<T> getList (String query) throws NotFoundException, CannotCloseConnection, RollbackException {
            List<T> results = new ArrayList<>();
            Connection con = DBManager.connect();

            try {
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    results.add(this.mapEntity(rs));
                }
            } catch (SQLException e) {
                try {
                    con.rollback();
                    throw new NotFoundException("No se encontro el registro buscado");
                } catch (SQLException e1) {
                    throw new RollbackException("Se rompio todo (?");
                }
            } finally {
                try {
                    con.close();
                } catch (SQLException e1) {
                    throw new CannotCloseConnection("Hubo un problema al cortar la conexión");
                }
            }
            return results;
        }
}

