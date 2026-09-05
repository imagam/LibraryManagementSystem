package exceptions;

public class DuplicateBookException extends Exception {
    public DuplicateBookException(String msg) {
        super(msg);
    }
}
