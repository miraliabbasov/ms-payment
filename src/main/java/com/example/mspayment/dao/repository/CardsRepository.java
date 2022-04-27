package com.example.mspayment.dao.repository;

import com.example.mspayment.dao.entity.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<CardsEntity, Long> {

    Optional<CardsEntity> findByCardNumber (String cardNumber);
}
