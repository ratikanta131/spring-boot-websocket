package in.co.sdrc.newsapp.exception;

public class DBOperationFailedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DBOperationFailedException(String message){
        super(message);
    }

}