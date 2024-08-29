package Exceptions;

public class RollbackException extends DAOException{
    public RollbackException(String message){
        super(message);
    }
}