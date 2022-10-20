package co.uk.worldpay.test.challenge.exception;

public class MerchantException extends Exception {

    public MerchantException(final String mesasge, final Throwable e) {
        super(mesasge, e);
    }

    public MerchantException(final String mesasge) {
        super(mesasge);
    }
}
