package com.company.university.dao;

public class DataBaseRuntimeException extends RuntimeException{
    
    private static final long serialVersionUID = -300881685767828937L;

    public DataBaseRuntimeException() {
    }

    public DataBaseRuntimeException(String message) {
        super(message);
    }

    public DataBaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseRuntimeException(Throwable cause) {
        super(cause);
    }
}
