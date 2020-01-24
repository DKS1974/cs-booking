package com.cs.etrading.csbooking.orders.model.validator;
import com.cs.etrading.csbooking.orders.model.Order;
import com.cs.etrading.csbooking.orders.model.OrderType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceNotNullValidator implements ConstraintValidator<PriceNotNull, Order> {
    @Override
    public boolean isValid(Order order, ConstraintValidatorContext constraintValidatorContext) {
        return OrderType.limit.equals(order.getType()) && order.getPrice() != null ||
                OrderType.market.equals(order.getType()) && order.getPrice() == null ;
    }
}