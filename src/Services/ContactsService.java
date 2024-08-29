package Services;

import DAOs.Impl.ContactsDAO;
import Entities.Contact;
import Exceptions.DAOException;
import Exceptions.ServiceException;

import java.util.List;

public class ContactsService {

    private final ContactsDAO dao = new ContactsDAO();


    public List<Contact> getAll(int id) throws ServiceException{
        String query = "select *  from contacts where UserId1 = " + id + " or UserId2 =" + id;
        try{
            return this.dao.getAll(query);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void create(Contact contact) throws ServiceException {

        if(contact.getContactId2() == contact.getContactId1())
            throw new ServiceException("No es posible agregarse a si mismo como contacto");

        String contactAlreadyExists = "SELECT * FROM Contacts Where (UserId1 = " + contact.getContactId1() + " AND UserId2 = " + contact.getContactId2() + ") OR (UserId1 = " + contact.getContactId2() + " AND UserId2 = " + contact.getContactId1() + ")";
        String query = "INSERT INTO Contacts(UserId1, UserId2) values('" + contact.getContactId1() + "', '"  + contact.getContactId2() + "')";
        try{

            Contact exists = this.dao.get(contactAlreadyExists);
            if(exists == null)
                this.dao.create(query);
            else
                throw new ServiceException("El usuario ya figura como un contacto");

        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(int contactId, int actualUserId) throws ServiceException {
        String query = "DELETE FROM Contacts Where (UserId1 = " + contactId + " AND UserId2 = " + actualUserId + ") OR (UserId1 = " + actualUserId + " AND UserId2 = " + contactId + ")";
        try{
            this.dao.delete(query);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
