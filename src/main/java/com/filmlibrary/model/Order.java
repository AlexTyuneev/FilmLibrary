package com.filmlibrary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "order_id_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Order
      extends GenericModel {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ORDER_USER"))
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "film_id", foreignKey = @ForeignKey(name = "FK_ORDER_FILM"))
    private Film film;

    
    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;
    //поле автоматически должно рассчитываться из rent_date + rent_period

    //rent_period - количество дней аренды, если не указано, то по-умолчанию - 14 дней
    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;
    @Column(name = "returned", nullable = false)
    private Boolean returned;

    @Column(name = "purchase", nullable = false)
    private boolean isPurchase;
}
