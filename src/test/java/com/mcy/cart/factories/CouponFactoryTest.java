package com.mcy.cart.factories;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mcy.McyShoppingCartApplication;
import com.mcy.coupon.Coupon;
import com.mcy.coupon.CouponAmountImpl;
import com.mcy.coupon.CouponRateImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = McyShoppingCartApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
public class CouponFactoryTest {
	CouponFactory factory;

	@Mock
	BeanFactory beanFactoryMock;

	@Before
	public void setUp() throws Exception {
		when(beanFactoryMock.getBean(CouponAmountImpl.class, 0.0, 0.0)).thenReturn(new CouponAmountImpl(0.0, 0.0));
		when(beanFactoryMock.getBean(CouponRateImpl.class, 0.0, 0.0)).thenReturn(new CouponRateImpl(0.0, 0.0));
		factory = new CouponFactory(beanFactoryMock);
	}

	@Test
	public void generateCampaign_whenDiscountTypeRate_thenReturnRateImpl() {
		Coupon campaign = factory.generateCoupon(0.0, 0.0, DiscountType.RATE);
		assertTrue(campaign instanceof CouponRateImpl);
	}

	@Test
	public void generateCampaign_whenDiscountTypeAmount_thenReturnAmountImpl() {
		Coupon campaign = factory.generateCoupon(0.0, 0.0, DiscountType.AMOUNT);
		assertTrue(campaign instanceof CouponAmountImpl);
	}
}
