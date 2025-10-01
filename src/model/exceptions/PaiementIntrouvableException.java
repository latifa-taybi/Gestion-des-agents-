package model.exceptions;

public class PaiementIntrouvableException extends RuntimeException {
    public PaiementIntrouvableException(String message) {
        super(message);
    }
}
