package DAOs;

import Exceptions.DAOException;

import java.util.List;

//Obliga a todos los DAOs a implementar todos los metodos definidos en la interface. Asegura consistencia en el codigo
//<T> va a ser una clase. El generico me permite que cualquier entidad tenga su dao. 
public interface GenericDAO<T> {

    void create(String query) throws DAOException;

    void delete(String query) throws DAOException;

    void update(String query) throws DAOException;

    T get(String query) throws DAOException;

    List<T> getAll(String query) throws DAOException;

    void tst() throws DAOException;
}
