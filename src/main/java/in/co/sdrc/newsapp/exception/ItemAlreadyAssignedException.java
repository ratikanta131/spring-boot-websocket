package in.co.sdrc.newsapp.exception;

public class ItemAlreadyAssignedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ItemAlreadyAssignedException(String message) {
        super(message);
    }
}