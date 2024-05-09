package com.mycompany.app.exception;

public class ThirdPartyException extends RuntimeException{
    private static final long serialVersionUID = 4493994161652408297L;

    public ThirdPartyException() {
        super();
    }

    public ThirdPartyException(String message) {
        super(message);
    }

    public ThirdPartyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdPartyException(Throwable cause) {
        super(cause);
    }

    protected ThirdPartyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
