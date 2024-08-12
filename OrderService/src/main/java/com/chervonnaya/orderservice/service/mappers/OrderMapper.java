package com.chervonnaya.orderservice.service.mappers;

import com.chervonnaya.orderdto.OrderDTO;
import com.chervonnaya.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {

    Order map(OrderDTO dto);
}
