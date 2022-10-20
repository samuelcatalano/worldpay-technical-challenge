package co.uk.worldpay.test.challenge.service;

import co.uk.worldpay.test.challenge.enums.StatusEnum;
import co.uk.worldpay.test.challenge.exception.MerchantException;
import co.uk.worldpay.test.challenge.exception.OfferException;
import co.uk.worldpay.test.challenge.model.Offer;
import co.uk.worldpay.test.challenge.repository.OfferRepository;
import co.uk.worldpay.test.challenge.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferService implements BaseService<Offer> {

    @Autowired
    private OfferRepository repository;

    @Autowired
    private MerchantService merchantService;

    /**
     * Returns all offer from a specific merchant.
     * @param merchantId the merchant id
     * @return all offer from a specific merchant
     * @throws OfferException the exception to be launched
     */
    public List<Offer> findAllByMerchant(final Long merchantId) throws OfferException {
        var offers = this.repository.findByMerchantId(merchantId).orElse(null);

        if (offers == null) {
            throw new OfferException(String.format("No offers from this merchant with ID: %s", merchantId));
        }

        return offers;
    }

    /**
     * Returns all offers saved.
     * @return all offers saved.
     */
    @Override
    public List<Offer> findAll() {
        return (List<Offer>) this.repository.findAll();
    }

    /**
     * Returns an offer by its id.
     * @param id the offer id
     * @return an offer by its id
     * @throws OfferException the exception to be launched
     */
    @Override
    public Offer findById(final Long id) throws OfferException {
        var offer = this.repository.findById(id).orElse(null);

        if (offer == null) {
            throw new OfferException(String.format("Offer not found with this ID: %s", id));
        }

        return offer;
    }

    /**
     * Save or update an offer.
     * @param entity an offer that will be saved or updated
     * @return a saved or updated offer
     */
    @Override
    public Offer saveOrUpdate(final Offer entity) {
        return this.repository.save(entity);
    }

    /**
     * Conclude an offer.
     * @param id the offer id
     * @return an offer concluded
     * @throws OfferException the exception to be launched
     */
    public Offer concludeOffer(final Long id) throws OfferException {
        var offer = this.repository.findById(id);

        if (!offer.isPresent()) {
            throw new OfferException(String.format("Offer not found with this ID: %s", id));
        }
        if (offer.get().getStatus().equals(StatusEnum.CONCLUDED)) {
            throw new OfferException("This offer is already concluded!");
        }

        offer.get().setStatus(StatusEnum.CONCLUDED);
        return this.repository.save(offer.get());
    }

    /**
     * Returns an updated offer.
     * @param offerId the offer id
     * @param merchantId the merchant id
     * @param offer the offer that wii be updated
     * @return an updated offer
     * @throws OfferException the exception to be launched
     * @throws MerchantException the exception to be launched
     */
    public Offer updateOffer(final Long offerId, final Long merchantId, final Offer offer) throws OfferException, MerchantException {
        var oldOffer = this.repository.findById(offerId);
        var merchant = this.merchantService.findById(merchantId);

        if (!oldOffer.isPresent()) {
            throw new OfferException(String.format("Offer not found with this ID: %s", offerId));
        }
        if (merchant == null) {
            throw new MerchantException(String.format("Merchant not found with this ID: %s", merchantId));
        }

        offer.setId(offerId);
        offer.setMerchant(merchant);
        offer.setCreationDate(oldOffer.get().getCreationDate());
        updateStatus(offer);

        return this.repository.save(offer);
    }

    /**
     * Update offer status from {@link StatusEnum}.
     * @param offer the offer that will get the status ypdated
     */
    private void updateStatus(final Offer offer) {
        if (offer.getCreationDate().plusMinutes(offer.getExpiration()).isBefore(LocalDateTime.now())) {
            offer.setStatus(StatusEnum.EXPIRED);
        } else {
            offer.setStatus(StatusEnum.AVAILABLE);
        }
    }
}
