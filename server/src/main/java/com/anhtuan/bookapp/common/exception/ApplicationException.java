package com.anhtuan.bookapp.common.exception;


public class ApplicationException extends Exception{
    private int errorCode;

    public ApplicationException(int message){
        super(String.valueOf(message));
        this.errorCode = message;
    }

    public ApplicationException(int code, String message){
        super(String.valueOf(message));
        this.errorCode = code;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
