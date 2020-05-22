package ro.fasttrackit.demorpsproject.exceptions;

public class SamePlayerException extends RuntimeException{
    public SamePlayerException(String message) {
        super(message);
    }
}
