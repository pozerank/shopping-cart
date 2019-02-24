package com.mcy.campaign;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mcy.cart.Discount;
import com.mcy.cart.ShoppingCart;
import com.mcy.category.CategoryParentable;

@Component
@Scope("prototype")
public class CampaignRateImpl extends Campaign {

	@Override
	public Discount applyFor(ShoppingCart cart) {
		Discount result = new Discount();
		result.setCategory(this.category);
		result.setOldSum(totalSum(cart.getProductCountMap()));
		result.setBenefit(benefit);
		result.setMinimumItemCount(minimumItemCount);
		result.setType(this.getClass().getName());
		double amount = 0.0d;
		if (isApplicable(cart.getProductCountMap())) {
			amount = result.getOldSum() * benefit / 100;
		}
		result.setDiscountSum(amount);
		return result;
	}

	public CampaignRateImpl(CategoryParentable category, double benefit, int minimumItemCount) {
		super(category, benefit, minimumItemCount);
	}
}
