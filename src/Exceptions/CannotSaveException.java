package Exceptions;

public class CannotSaveException extends  DAOException{

    public CannotSaveException(String message){
        super(message);
    }
}
