package com.mcy.campaign;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mcy.cart.Discount;
import com.mcy.cart.Product;
import com.mcy.cart.ShoppingCart;
import com.mcy.category.Category;
import com.mcy.category.CategoryParentable;

public class CampaignTest {
	Campaign campaign;
	ConcurrentMap<Product, AtomicInteger> productCountMap;
	CategoryParentable category;

	@Before
	public void setUp() throws Exception {
		campaign = new Campaign() {
			@Override
			public Discount applyFor(ShoppingCart cart) {
				return null;
			}
		};
		campaign.setMinimumItemCount(1);
		category = new Category("test");
		campaign.setCategory(category);
		productCountMap = new ConcurrentHashMap<Product, AtomicInteger>();
	}

	@After
	public void tearDown() throws Exception {
		campaign = null;
		productCountMap = null;
	}

	@Test
	public void isApplicable_whenEmptyProductMapAndMinimumItemBiggerThanZero_thenReturnsFalse() {
		campaign.setMinimumItemCount(1);
		boolean actual = campaign.isApplicable(productCountMap);
		assertFalse(actual);
	}

	@Test
	public void isApplicable_whenNotEmptyProductMapAndMinimumItemBiggerThanZero_thenReturnsTrue() {
		productCountMap.put(new Product("product", 1.0, category), new AtomicInteger(1));
		boolean actual = campaign.isApplicable(productCountMap);
		assertTrue(actual);
	}

	@Test
	public void totalSum_whenNotEmptyProductMap_thenReturnsSum() {
		productCountMap.put(new Product("product", 1.0, category), new AtomicInteger(5));
		double actual = campaign.totalSum(productCountMap);
		assertEquals(5.0, actual,0);
	}
}
