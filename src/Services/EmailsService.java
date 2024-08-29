package Services;

import DAOs.Impl.EmailDAO;
import Entities.Email;
import Entities.EmailTypes;
import Entities.User;
import Exceptions.*;

import java.util.List;

public class EmailsService {

    private EmailDAO dao = new EmailDAO();
    private JavaMailService mailService = new JavaMailService();

    public void create(Email email) throws ServiceException {
        String query = "INSERT INTO emails(To, FromUser, Subject, Body,Host, State, AccountId) values ('" + email.getTo() + "', '"  + email.getFrom() +  "', '" + email.getSubject() +  "', '" + email.getBody() +  "', '" + email.getHost() +  "', '" + email.getType().ordinal() +  "', '" + email.getAccountId() + "')";

        try {
            this.dao.create(query);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean send(Email email, User user) throws ServiceException {
        try {
            this.mailService.SendEmail(email, user);
            return true;
        }
        catch (EmailException e){
            throw new ServiceException("Hubo un problema al enviar el mail");
        }
    }

    public List<Email> getAllRecievedByUserId(int userId) throws ServiceException {
        String query = "SELECT * FROM Emails WHERE State = " + EmailTypes.Recieved.ordinal() + "AND AccountId = " + userId;
        List<Email> results;
        try{
            results = this.dao.getAll(query);
        }
        catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return results;
    }

    public List<Email> getAllPendingByUserId(int userId) throws ServiceException {
        String query = "SELECT * FROM Emails WHERE State = " + EmailTypes.Pending.ordinal() + "AND AccountId = " + userId;
        List<Email> results;
        try{
            results = this.dao.getAll(query);
        }
        catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return results;
    }

    public List<Email> getAllSentByUserId(String email) throws ServiceException {
        String query = "SELECT * FROM Emails WHERE State = " + EmailTypes.Sent.ordinal() + "AND FromUser = '" + email + "'";
        List<Email> results;
        try{
            results = this.dao.getAll(query);
        }
        catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return results;
    }

    public void update(int id, Email newEmail) throws ServiceException {
        Email email = this.fetchEmail(id);
        Email emailForUpdating = this.merge(email, newEmail);

        String query = "UPDATE EMAILS SET To = '" + emailForUpdating.getTo() + "', Subject = '"  + emailForUpdating.getSubject() +  "', Body = '" + emailForUpdating.getBody() +  "', State = '" + emailForUpdating.getType().ordinal() +  "' WHERE Id =" + id;
        try {
            this.dao.update(query);
        }
        catch (DAOException e ) {
            throw new ServiceException("Hubo un problema al actualizar el email con id" + id);
        }
    }


    public void delete(int id) throws ServiceException {
        try{
            String query = "DELETE FROM EMAILS WHERE Id = " + id;
            this.dao.delete(query);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteByAccountIdAndType(int accountId, int emailType) throws ServiceException {
        try{
            String query = "DELETE FROM EMAILS WHERE AccountId = " + accountId + " AND State = " + emailType;
            this.dao.delete(query);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private Email fetchEmail(int id) throws ServiceException {
        Email result;
        try{
            String query = "SELECT * FROM EMAILS WHERE ID = " + id;

            result = this.dao.get(query);
        } catch (DAOException e) {
            throw new ServiceException("Hubo un problema al tratar de obtener el mail con id " + id);
        }
        return result;
    }

    private Email merge(Email oldEmail, Email newEmail){
        EmailTypes type = newEmail.getType() == null ? oldEmail.getType() : newEmail.getType();
        String to = newEmail.getTo() == null ? oldEmail.getTo() : newEmail.getTo();
        String body = newEmail.getBody() == null ? oldEmail.getBody() : newEmail.getBody();
        String subject = newEmail.getSubject() == null ? oldEmail.getSubject() : newEmail.getSubject();

        return new Email(body, to, oldEmail.getFrom(), subject, oldEmail.getHost(), type, oldEmail.getAccountId(), oldEmail.getId());
    }


    public void sendAndRecieve(User user) throws ServiceException {
        List pendingEmails = getOutbox(user);

        for (int index = 0; index < pendingEmails.size(); index++)
        {
             Email currEmail = (Email) pendingEmails.get(index);
             boolean success = send(currEmail,  user);
             if(success) {
                 markAsSent(user.getId());
             }
        }
        JavaMailService javaMailService = new JavaMailService();
        try {
            List recievedEmails = javaMailService.recieve(user);

            deleteByAccountIdAndType(user.getId(), EmailTypes.Recieved.ordinal());

            recievedEmails.forEach(email -> {
                try {
                    create((Email) email);
                } catch (ServiceException e) { }

            });
        }
        catch (EmailException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    private void markAsSent(int accountId) throws ServiceException {
        String query = "UPDATE Emails set State = " + EmailTypes.Sent.ordinal() + " WHERE State = " + EmailTypes.Outbox.ordinal() + "AND AccountId = " + accountId;
        try {
            this.dao.update(query);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public List<Email> getOutbox(User user) throws ServiceException {
        String query = "SELECT * FROM Emails WHERE State = " + EmailTypes.Outbox.ordinal() + "AND FromUser = '" + user.getName() + "'";
        List<Email> results;
        try{
            results = this.dao.getAll(query);
        }
        catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return results;
    }

    public void tst() throws DAOException {
        this.dao.tst();
    }

}
