package com.example.mspayment.model.map;

import com.example.mspayment.dao.entity.PaymentEntity;
import com.example.mspayment.model.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class PaymentMap {

    public static final PaymentMap INSTANCE = Mappers.getMapper(PaymentMap.class);

    public abstract PaymentDto entityToDto(PaymentEntity entity);

}
