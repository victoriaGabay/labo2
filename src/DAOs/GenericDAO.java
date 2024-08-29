package DAOs;

import Exceptions.DAOException;

import java.util.List;

public interface GenericDAO<T> {

    void create(String query) throws DAOException;

    void delete(String query) throws DAOException;

    void update(String query) throws DAOException;

    T get(String query) throws DAOException;

    List<T> getAll(String query) throws DAOException;

    void tst() throws DAOException;
}
