package model.exceptions;

public class MontantNegatifException extends RuntimeException {
    public MontantNegatifException(String message) {
        super(message);
    }
}
