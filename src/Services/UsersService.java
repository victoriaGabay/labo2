package Services;

import DAOs.Impl.UsersDAO;
import Entities.Contact;
import Entities.User;
import Exceptions.DAOException;
import Exceptions.ServiceException;

import java.util.ArrayList;

public class UsersService {

    private final UsersDAO dao = new UsersDAO();

    public void create(User user) throws ServiceException{

        String checkQuery = "SELECT * FROM Users WHERE UserName = '" + user.getName() + "'";
        String query = "INSERT INTO Users(Username, Password) values ('" + user.getName() + "', '"  + user.getPassword() + "')";
        try{
            User result = this.dao.get(checkQuery);

            if(result == null)
                this.dao.create(query);
            else
                throw new ServiceException("El nombre de usuario ya esta en uso.");

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public User get(int userId) throws ServiceException {
        String query = "SELECT * FROM Users WHERE Id = " + userId;

        try{
            User user = this.dao.get(query);

            if(user == null){
                throw new ServiceException("No se encontro el registro buscado");
            }
            return  user;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public User getByName(String username) throws ServiceException {
        String query = "SELECT * FROM Users WHERE UserName = '" + username + "'";

        try{
            User user = this.dao.get(query);

            if(user == null){
                throw new ServiceException("El nombre de usuario ingresado no existe");
            }
            return  user;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    public User logIn(String username, String password) throws ServiceException {
        String query = "SELECT * FROM Users WHERE UserName = '" + username + "' AND Password = '" + password + "'";

        try{
            User user = this.dao.get(query);

            if(user == null){
                throw new ServiceException("No se encontro el registro buscado");
            }
            return  user;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void updateHostConfiguration(User user) throws ServiceException {
        String query = "UPDATE Users SET Host = '" + user.getHost() + "', Port = '" + user.getPort() + "', HostUserName = '" + user.getHostUserName() + "', HostPassword = '" + user.getHostPassword() + "', PopHost = '" + user.getPopHost() + "', PopHostPort = '" + user.getPopHostPort() + "' WHERE Id = " + user.getId();
        try{
            this.dao.update(query);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<User> getAllAContacts(int actualUserId) throws ServiceException {
        ArrayList<User> list = new ArrayList<>();
        ContactsService contactsService = new ContactsService();
        ArrayList<Contact> contacts = (ArrayList<Contact>) contactsService.getAll(actualUserId);

        for(int index = 0; index < contacts.toArray().length; index++){
            Contact contact = contacts.get(index);

            int contactId = contact.getContactId1() == actualUserId ? contact.getContactId2() : contact.getContactId1();
            User contactUser = this.get(contactId);
            list.add(contactUser);
        }

        return list;
    }

}
