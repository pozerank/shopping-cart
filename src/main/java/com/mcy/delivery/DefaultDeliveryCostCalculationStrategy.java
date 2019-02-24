package com.mcy.delivery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mcy.cart.ShoppingCart;

@Component("defaultDeliveryCostCalculationStrategy")
public class DefaultDeliveryCostCalculationStrategy implements DeliveryCostCalculable {

	@Value("${mcy.delivery.algorithm.default.costPerDelivery}")
	private double costPerDelivery;

	@Value("${mcy.delivery.algorithm.default.costPerProduct}")
	private double costPerProduct;

	@Value("${mcy.delivery.algorithm.default.fixedCost}")
	private double fixedCost;

	@Override
	public double calculate(ShoppingCart cart) {
		double deliveryCost = 0.0d;
		int numberOfDeliveries = calculateNumberOfDeliveries(cart);
		int numberOfProducts = calculateNumberOfProducts(cart);
		deliveryCost = (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;
		return deliveryCost;
	}

	private int calculateNumberOfProducts(ShoppingCart cart) {
		return cart.getProductCountMap().entrySet().stream().mapToInt(e -> e.getValue().intValue()).sum();
	}

	private int calculateNumberOfDeliveries(ShoppingCart cart) {
		return cart.getCategoryProductListMap().size();
	}

	@Override
	public String toString() {
		return "DefaultDeliveryCostCalculationStrategy => ALGORITHM : (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost.\nVALUES [costPerDelivery="
				+ costPerDelivery + ", costPerProduct=" + costPerProduct + ", fixedCost=" + fixedCost + "] ";
	}

}
