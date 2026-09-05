package exceptions;

public class LoanNotFoundException extends Exception {
    public LoanNotFoundException(String msg) {
        super(msg);
    }
}
