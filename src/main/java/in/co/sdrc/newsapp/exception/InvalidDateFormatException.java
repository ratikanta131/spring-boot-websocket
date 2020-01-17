package in.co.sdrc.newsapp.exception;

public class InvalidDateFormatException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidDateFormatException(String message) {
        super(message);
    }

}