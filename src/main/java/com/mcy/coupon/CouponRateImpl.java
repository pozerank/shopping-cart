package com.mcy.coupon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mcy.cart.ShoppingCart;

@Component
@Scope("prototype")
public class CouponRateImpl extends Coupon {

	@Override
	public double applyFor(ShoppingCart cart) {
		double result = 0.0d;
		if (isApplicable(cart.getTotalAmountAfterDiscounts())) {
			double totalSum = cart.getTotalAmountAfterDiscounts();
			result = totalSum * getBenefit() / 100;
		}
		return result;
	}

	public CouponRateImpl(double thresholdAmount, double benefit) {
		super(thresholdAmount, benefit);
	}

}
