package exceptions;

public class BookAlreadyReturnedException extends Exception {
    public BookAlreadyReturnedException(String msg)
    {
        super(msg);
    }
}
