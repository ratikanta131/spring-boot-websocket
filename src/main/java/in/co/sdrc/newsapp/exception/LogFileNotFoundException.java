package in.co.sdrc.newsapp.exception;

public class LogFileNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public LogFileNotFoundException(String message) {
        super(message);
    }
}