package co.uk.worldpay.test.challenge.service;

import co.uk.worldpay.test.challenge.exception.MerchantException;
import co.uk.worldpay.test.challenge.model.Merchant;
import co.uk.worldpay.test.challenge.repository.MerchantRepository;
import co.uk.worldpay.test.challenge.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService implements BaseService<Merchant> {

    @Autowired
    private MerchantRepository repository;

    /**
     * Returns all offers saved.
     * @return all offers saved.
     */
    @Override
    public List<Merchant> findAll() {
        return (List<Merchant>) this.repository.findAll();
    }

    /**
     * Returns an merchant by its id.
     * @param id the merchant id
     * @return an merchant by its id
     * @throws MerchantException the exception to be launched
     */
    @Override
    public Merchant findById(final Long id) throws MerchantException {
        var merchant = this.repository.findById(id).orElse(null);

        if (merchant == null) {
            throw new MerchantException(String.format("Merchant not found with this ID: %s", id));
        }

        return merchant;
    }

    /**
     * Returns a merchant by its name.
     * @param name the merchant name
     * @return a merchant by its name
     */
    public Merchant findByName(final String name) {
        return this.repository.findByName(name);
    }

    /**
     * Save or update an offer.
     * @param entity an offer that will be saved or updated
     * @return a saved or updated offer
     */
    @Override
    public Merchant saveOrUpdate(final Merchant entity) throws Exception {
        return this.repository.save(entity);
    }
}
