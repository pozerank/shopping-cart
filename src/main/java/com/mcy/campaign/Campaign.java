package com.mcy.campaign;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mcy.cart.Discount;
import com.mcy.cart.Product;
import com.mcy.cart.ShoppingCart;
import com.mcy.category.CategoryParentable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Campaign {

	protected CategoryParentable category;
	protected double benefit;
	protected int minimumItemCount;

	public abstract Discount applyFor(ShoppingCart cart);

	protected boolean isApplicable(ConcurrentMap<Product, AtomicInteger> productCountMap) {
		boolean result = false;

		int categoryTotalProductCount = productCountMap.entrySet().stream()
				.filter(e -> e.getKey().getCategory().getTitle().equalsIgnoreCase(category.getTitle()))
				.mapToInt(e -> e.getValue().intValue()).sum();
		if (minimumItemCount <= categoryTotalProductCount) {
			result = true;
		}
		return result;

	}

	protected double totalSum(ConcurrentMap<Product, AtomicInteger> productCountMap) {
		return productCountMap.entrySet().parallelStream()
				.mapToDouble(a -> a.getKey().getPrice() * a.getValue().doubleValue()).sum();

	}
}
