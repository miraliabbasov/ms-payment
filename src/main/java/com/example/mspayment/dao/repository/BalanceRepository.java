package com.example.mspayment.dao.repository;

import com.example.mspayment.dao.entity.PaymentOfBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<PaymentOfBalanceEntity,Long> {
}
