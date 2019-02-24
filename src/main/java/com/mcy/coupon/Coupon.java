package com.mcy.coupon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mcy.cart.ShoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Coupon {

	private double thresholdAmount;
	private double benefit;

	/**
	 * @param productList
	 * @return
	 */
	public abstract double applyFor(ShoppingCart cart);

	/**
	 * @param productList
	 * @return
	 */
	protected boolean isApplicable(double totalAmountAfterDiscounts) {
		return totalAmountAfterDiscounts >= thresholdAmount ? true : false;
	}

}
