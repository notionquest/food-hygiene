package com.food.hygiene.exception;

public class FoodHygieneApplicationException extends RuntimeException {

    private static final long serialVersionUID = 8062649343608667178L;

    String errorMessage;
    String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public FoodHygieneApplicationException() {
        super();
    }

    public FoodHygieneApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public FoodHygieneApplicationException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public FoodHygieneApplicationException(String message, String errorCode) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
    }

    public FoodHygieneApplicationException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.errorCode = errorCode;
    }

    public FoodHygieneApplicationException(Throwable cause) {
        super(cause);
    }

}
