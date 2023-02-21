package com.filmlibrary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "order_id_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Order
      extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_BOOK"))
    private Film film;

    
    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;
    //поле автоматически должно рассчитываться из rent_date + rent_period

    //rent_period - количество дней аренды, если не указано, то по-умолчанию - 14 дней
    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;

    @Column(name = "purchase", nullable = false)
    private boolean isPurchase;
}
