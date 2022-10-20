package co.uk.worldpay.test.challenge.repository;

import co.uk.worldpay.test.challenge.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends CrudRepository<Offer, Long> {

    Optional<List<Offer>> findByMerchantId(final Long merchantId);
}
