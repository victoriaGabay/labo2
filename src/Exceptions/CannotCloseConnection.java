package Exceptions;

public class CannotCloseConnection extends DAOException{
    public CannotCloseConnection(String message){
        super(message);
    }
}
