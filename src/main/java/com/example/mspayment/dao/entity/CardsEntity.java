package com.example.mspayment.dao.entity;

import com.example.mspayment.model.enums.Constants;
import com.example.mspayment.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cards")
public class CardsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "card_number")
    String cardNumber;

    BigDecimal balance;

    @Enumerated(value = EnumType.STRING)
    Status status;


}
