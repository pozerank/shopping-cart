package com.mcy.cart.factories;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mcy.coupon.Coupon;
import com.mcy.coupon.CouponAmountImpl;
import com.mcy.coupon.CouponRateImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CouponFactory {
	private BeanFactory beanFactory;

	@Autowired
	public CouponFactory(BeanFactory beanFactory) {
		super();
		this.beanFactory = beanFactory;
	}

	public Coupon generateCoupon(double thresholdAmount, double benefit, DiscountType discountType) {
		Coupon result = null;
		switch (discountType) {
		case AMOUNT:
			result = beanFactory.getBean(CouponAmountImpl.class, thresholdAmount, benefit);
			break;
		case RATE:
			result = beanFactory.getBean(CouponRateImpl.class, thresholdAmount, benefit);
			break;
		default:
			log.error("UNDEFINED DISCOUNT TYPE");
			break;
		}
		return result;
	}

}
