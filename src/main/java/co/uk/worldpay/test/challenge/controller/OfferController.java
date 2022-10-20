package co.uk.worldpay.test.challenge.controller;

import co.uk.worldpay.test.challenge.enums.StatusEnum;
import co.uk.worldpay.test.challenge.exception.BusinessException;
import co.uk.worldpay.test.challenge.exception.MerchantException;
import co.uk.worldpay.test.challenge.exception.OfferException;
import co.uk.worldpay.test.challenge.model.Merchant;
import co.uk.worldpay.test.challenge.model.Offer;
import co.uk.worldpay.test.challenge.service.MerchantService;
import co.uk.worldpay.test.challenge.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/worldpay/offer")
public class OfferController {

    @Autowired
    private OfferService service;

    @Autowired
    private MerchantService merchantService;

    /**
     * Returns an offer by its id.
     * @param offerId the offer id
     * @return an offer by its id
     * @throws BusinessException the exception to be launched
     */
    @GetMapping(value = "/{offerId}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable(value = "offerId") final Long offerId) throws BusinessException {
        try {
            var offer = this.service.findById(offerId);
            return ResponseEntity.ok(offer);

        } catch (final OfferException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Save or update an offer.
     * @param merchantId the merchant id
     * @param offer the offer id
     * @return a saved or updated offer
     * @throws BusinessException the exception to be launched
     */
    @PostMapping(value = "/merchant/{merchantId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveNewOffer(@PathVariable(value = "merchantId") final Long merchantId, @RequestBody final Offer offer) throws BusinessException {
        final Merchant merchant;

        try {
            merchant = this.merchantService.findById(merchantId);
        } catch (final MerchantException e) {
            throw new BusinessException(e.getMessage());
        }

        offer.setMerchant(merchant);
        offer.setStatus(StatusEnum.AVAILABLE);

        var savedOffer = this.service.saveOrUpdate(offer);
        return ResponseEntity.ok(savedOffer);
    }

    /**
     * Returns all offers from a specific merchant.
     * @param merchantId the merchant id
     * @return all offers from a specific merchant
     * @throws BusinessException the exception to be launched
     */
    @GetMapping(value = "/merchant/{merchantId}", produces = "application/json")
    public ResponseEntity<?> findAllOffersByMerchant(@PathVariable(value = "merchantId") final Long merchantId) throws BusinessException {
        try {
            var offers = this.service.findAllByMerchant(merchantId);
            return ResponseEntity.ok(offers);

        } catch (final OfferException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns an offer concluded and updated.
     * @param offerId the offer id
     * @return an offer concluded and updated
     * @throws BusinessException the exception to be launched
     */
    @PutMapping(value = "/conclude/{offerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> concludeOffer(@PathVariable(value = "offerId") final Long offerId) throws BusinessException {
        try {
            var offer = this.service.concludeOffer(offerId);
            return ResponseEntity.ok(offer);

        } catch (final OfferException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Returns an updated offer.
     * @param offerId the offer id
     * @param merchantId the merchant id
     * @param newOffer the offer with the updated fields/parameters
     * @return an updated offer.
     * @throws BusinessException the exception to be launched
     */
    @PutMapping(value = "/update/{merchantId}/{offerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateOffer(
            @PathVariable(value = "offerId") final Long offerId,
            @PathVariable(value = "merchantId") final Long merchantId,
            @RequestBody final Offer newOffer) throws BusinessException {

        try {
            var offer = this.service.updateOffer(offerId, merchantId, newOffer);
            return ResponseEntity.ok(offer);

        } catch (OfferException | MerchantException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}