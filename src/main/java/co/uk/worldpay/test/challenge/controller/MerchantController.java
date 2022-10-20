package co.uk.worldpay.test.challenge.controller;

import co.uk.worldpay.test.challenge.exception.BusinessException;
import co.uk.worldpay.test.challenge.exception.MerchantException;
import co.uk.worldpay.test.challenge.model.Merchant;
import co.uk.worldpay.test.challenge.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/worldpay/merchant")
public class MerchantController {

    @Autowired
    private MerchantService service;

    /**
     * Save or update a merchant into the database.
     * @param merchant the merchant to be saved
     * @return a saved or updated merchant
     * @throws BusinessException exception to be launched
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveNewMerchant(@RequestBody final Merchant merchant) throws BusinessException {
        try {
            var result = this.service.saveOrUpdate(merchant);
            return ResponseEntity.ok(result);

        } catch (final Exception e) {
            throw new BusinessException("Error saving new merchant!", e.getCause());
        }
    }

    @GetMapping(value = "/{merchantId}", produces = "application/json")
    public ResponseEntity<?> findMerchantById(@PathVariable(value = "merchantId") final Long merchantId) throws BusinessException {
        try {
            var result = this.service.findById(merchantId);
            return ResponseEntity.ok(result);

        } catch (final MerchantException e) {
            throw new BusinessException("Error getting merchant with this ID!", e.getCause());
        }
    }
}