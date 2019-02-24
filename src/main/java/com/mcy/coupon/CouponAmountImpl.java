package com.mcy.coupon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mcy.cart.ShoppingCart;

@Component
@Scope("prototype")
public class CouponAmountImpl extends Coupon {

	@Override
	public double applyFor(ShoppingCart cart) {
		double result = 0.0d;
		if (isApplicable(cart.getTotalAmountAfterDiscounts())) {
			double totalSum = cart.getTotalAmountAfterDiscounts();
			result = getBenefit() > totalSum ? totalSum : getBenefit();
		}
		return result;
	}

	public CouponAmountImpl(double thresholdAmount, double benefit) {
		super(thresholdAmount, benefit);
	}
}
