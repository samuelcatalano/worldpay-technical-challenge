package co.uk.worldpay.test.challenge.service.base;

import co.uk.worldpay.test.challenge.model.base.BaseEntity;
import java.util.List;

public interface BaseService<E extends BaseEntity> {

    List<E> findAll() throws Exception;
    E findById(final Long id) throws Exception;
    E saveOrUpdate(final E entity) throws Exception;

}
