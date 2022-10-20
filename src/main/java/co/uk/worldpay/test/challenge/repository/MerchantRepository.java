package co.uk.worldpay.test.challenge.repository;

import co.uk.worldpay.test.challenge.model.Merchant;
import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    Merchant findByName(final String name);
}
