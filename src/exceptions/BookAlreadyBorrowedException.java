package exceptions;

public class BookAlreadyBorrowedException extends Exception {
    public BookAlreadyBorrowedException(String msg){
        super(msg);
    }
}
