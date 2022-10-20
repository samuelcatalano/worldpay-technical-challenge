package co.uk.worldpay.test.challenge.model;

import co.uk.worldpay.test.challenge.enums.StatusEnum;
import co.uk.worldpay.test.challenge.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "offer")
public class Offer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    private String description;
    private String currency;
    private Double price;
    private LocalDateTime creationDate;
    private Long expiration; // in minutes

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;
}