package ro.fasttrackit.demorpsproject.exceptions;

public class PlayerLimitException extends RuntimeException {
    public PlayerLimitException(String message) {
        super(message);
    }
}
