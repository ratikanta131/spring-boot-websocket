package in.co.sdrc.newsapp.exception;

public class ItemIdAlreadyExistsException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ItemIdAlreadyExistsException(String message){
        super(message);
    }

}