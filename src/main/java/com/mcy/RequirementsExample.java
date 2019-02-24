//package com.mcy;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import com.mcy.campaign.Campaign;
//import com.mcy.cart.Discount;
//import com.mcy.cart.Product;
//import com.mcy.cart.ShoppingCart;
//import com.mcy.cart.factories.CampaignFactory;
//import com.mcy.cart.factories.CouponFactory;
//import com.mcy.cart.factories.DiscountType;
//import com.mcy.category.Category;
//import com.mcy.category.CategoryParentable;
//import com.mcy.coupon.Coupon;
//import com.mcy.delivery.DeliveryCostCalculatorFacade;
//
//@Controller
//public class RequirementsExample {
//
//	@Autowired
//	private BeanFactory beanFactory;
//
//	@Autowired
//	private CampaignFactory campaignFactory;
//
//	@Autowired
//	private CouponFactory couponFactory;
//
//	@Autowired
//	private DeliveryCostCalculatorFacade deliveryCostCalculator;
//
//	@PostConstruct
//	private void init() {
//
//		CategoryParentable cat = new Category("food");
//
//		Product apple = new Product("Apple", 100.0, cat);
//		Product almond = new Product("Almond", 150.0, cat);
//
//		ShoppingCart cart = beanFactory.getBean(ShoppingCart.class);
//		cart.addItem(apple, 3);
//		cart.addItem(almond, 1);
//
//		Campaign campaign1 = campaignFactory.generateCampaign(cat, 20.0, 3, DiscountType.RATE);
//		Campaign campaign2 = campaignFactory.generateCampaign(cat, 50.0, 5, DiscountType.RATE);
//		Campaign campaign3 = campaignFactory.generateCampaign(cat, 5.0, 5, DiscountType.AMOUNT);
//
//		Discount discount1 = campaign1.applyFor(cart);
//		Discount discount2 = campaign2.applyFor(cart);
//		Discount discount3 = campaign3.applyFor(cart);
//
//		cart.applyDiscounts(discount1, discount2, discount3);
//
//		Coupon coupon = couponFactory.generateCoupon(100.0, 10, DiscountType.RATE);
//
//		cart.applyCoupon(coupon);
//
//		cart.print();
//	}
//
//}
