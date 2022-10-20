package co.uk.worldpay.test.challenge.exception;

public class OfferException extends Exception {

    public OfferException(final String mesasge, final Throwable e) {
        super(mesasge, e);
    }

    public OfferException(final String mesasge) {
        super(mesasge);
    }
}
