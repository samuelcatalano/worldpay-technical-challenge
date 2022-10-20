package co.uk.worldpay.test.challenge.model;

import co.uk.worldpay.test.challenge.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "merchant")
public class Merchant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    private String name;

    @OneToMany(mappedBy = "merchant")
    private List<Offer> offers;
}
