package com.app.quantitymeasurement.exception;


public class QuantityMeasurementException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    // Message-only constructor
    public QuantityMeasurementException(String message) {
        super(message);
        this.errorCode = null;
    }

    // Message + cause constructor
    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    // Message + errorCode constructor
    public QuantityMeasurementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "QuantityMeasurementException{" +
                "message=" + getMessage() +
                (errorCode != null ? ", code=" + errorCode : "") +
                '}';
    }
}
