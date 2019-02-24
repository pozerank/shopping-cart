package com.mcy.campaign;

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

public class CampaignRateImplTest {
	Campaign campaign;
	ConcurrentMap<Product, AtomicInteger> productCountMap;
	CategoryParentable category;

	@Before
	public void setUp() throws Exception {
		category = new Category("test");
		campaign = new CampaignRateImpl(category, 5, 1);
		campaign.setMinimumItemCount(1);
		campaign.setCategory(category);
		productCountMap = new ConcurrentHashMap<Product, AtomicInteger>();
	}

	@After
	public void tearDown() throws Exception {
		campaign = null;
		productCountMap = null;
	}

	@Test
	public void applyFor_whenCategorySame_thenAddDiscount() {
		campaign.setMinimumItemCount(1);
		ShoppingCart cart = new ShoppingCart();
		productCountMap.put(new Product("product", 1.0, category), new AtomicInteger(1));
		cart.setProductCountMap(productCountMap);
		Discount actual = campaign.applyFor(cart);
		assertTrue(actual.getDiscountSum() != 0);
	}

	@Test
	public void applyFor_whenCategoryNotSame_thenNoDiscount() {
		Category category2 = new Category("test2");
		campaign.setMinimumItemCount(1);
		ShoppingCart cart = new ShoppingCart();
		productCountMap.put(new Product("product", 1.0, category2), new AtomicInteger(1));
		cart.setProductCountMap(productCountMap);
		Discount actual = campaign.applyFor(cart);
		assertTrue(actual.getDiscountSum() == 0);
	}

}
