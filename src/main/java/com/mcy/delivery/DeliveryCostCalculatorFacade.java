package com.mcy.delivery;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mcy.cart.ShoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCostCalculatorFacade {

	@Autowired
	BeanFactory beanFactory;

	@Value("${mcy.delivery.cost-calculate-algorithm}")
	private String delivertCostCalculatorStrategy;

	public double calculateFor(ShoppingCart cart) {
		return beanFactory.getBean(delivertCostCalculatorStrategy, DeliveryCostCalculable.class).calculate(cart);
	}

	public String getStrategyInformation() {
		return beanFactory.getBean(delivertCostCalculatorStrategy, DeliveryCostCalculable.class).toString();
	}

}
