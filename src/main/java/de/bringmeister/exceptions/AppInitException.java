package de.bringmeister.exceptions;

public class AppInitException extends RuntimeException {
    public AppInitException(Throwable e) {
        super(e);
    }
}
