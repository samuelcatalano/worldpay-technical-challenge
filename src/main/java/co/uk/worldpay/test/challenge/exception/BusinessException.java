package co.uk.worldpay.test.challenge.exception;

public class BusinessException extends Exception {

    public BusinessException(final String mesasge, final Throwable e) {
        super(mesasge, e);
    }

    public BusinessException(final String mesasge) {
        super(mesasge);
    }
}
