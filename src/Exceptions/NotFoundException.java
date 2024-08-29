package Exceptions;

public class NotFoundException  extends DAOException{

    public NotFoundException(String message){
        super(message);
    }
}
