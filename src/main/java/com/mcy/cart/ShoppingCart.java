package com.mcy.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mcy.category.CategoryParentable;
import com.mcy.coupon.Coupon;
import com.mcy.delivery.DeliveryCostCalculatorFacade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Scope("prototype")
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

	@Autowired
	DeliveryCostCalculatorFacade deliveryCostCalculator;

	private Coupon coupon;
	private ConcurrentMap<Product, AtomicInteger> productCountMap = new ConcurrentHashMap<Product, AtomicInteger>();
	private ConcurrentMap<CategoryParentable, List<Product>> categoryProductListMap = new ConcurrentHashMap<CategoryParentable, List<Product>>();

	private List<Discount> campaignDiscountList = new ArrayList<Discount>();
	private double totalAmountAfterDiscounts;

	public void addItem(Product product, int quantity) {
		productCountMap.putIfAbsent(product, new AtomicInteger(0));
		productCountMap.get(product).addAndGet(quantity);

		categoryProductListMap.putIfAbsent(product.getCategory(), new ArrayList<Product>());
		categoryProductListMap.get(product.getCategory()).add(product);
	}

	public void applyCoupon(Coupon coupon) {
		if (null != coupon) {
			this.coupon = coupon;
		} else {
			log.error("COUPON IS ALREADY APPLIED");
		}
	}

	public void applyDiscounts(Discount... discountArray) {
		campaignDiscountList.clear();
		campaignDiscountList.addAll(Arrays.asList(discountArray));
	}

	protected double totalSum() {
		return productCountMap.entrySet().parallelStream()
				.mapToDouble(a -> a.getKey().getPrice() * a.getValue().doubleValue()).sum();

	}

	public double getTotalAmountAfterDiscounts() {
		double totalAmountAfterDiscount = totalSum() - getCampaignDiscount();
		return totalAmountAfterDiscount < 0.0 ? 0.0 : totalAmountAfterDiscount;
	}

	public double getCouponDiscount() {
		double result = 0.0d;
		if (null != this.coupon) {
			result = coupon.applyFor(this);
		}
		return result;
	}

	public double getCampaignDiscount() {
		double result = 0.0d;
		if (Objects.nonNull(campaignDiscountList)) {
			result = campaignDiscountList.stream().mapToDouble(e -> e.getDiscountSum()).sum();
		}
		return result;
	}

	public double getDeliveryCost() {
		return deliveryCostCalculator.calculateFor(this);
	}

	public void print() {
		for (Entry<CategoryParentable, List<Product>> category : categoryProductListMap.entrySet()) {
			log.info("-----------------------------------");
			log.info("CATEGORY {}", category.getKey());
			log.info("-----------------------------------");
			List<Product> productList = category.getValue();
			for (Product product : productList) {
				log.info("{}= unitPrice: {}, count:{}.", product.getTitle(), product.getPrice(),
						productCountMap.get(product));
			}
			log.info("-----------------------------------");
			log.info("APPLIED CAMPAIGNS");
			log.info("-----------------------------------");
			for (Discount discount : campaignDiscountList) {
				log.info("CATEGORY: {}, TYPE: {}, MIN_COUNT:{}, BENEFIT:{}, OLD SUM: {}, DISCOUNT: {}",
						discount.getCategory(), discount.getType(), discount.getMinimumItemCount(),
						discount.getBenefit(), discount.getOldSum(), discount.getDiscountSum());
			}
			setTotalAmountAfterDiscounts(getTotalAmountAfterDiscounts());
			log.info("-----------------------------------");
			log.info("APPLIED COUPON");
			log.info("-----------------------------------");
			log.info("TYPE: {}, BENEFIT: {}, THRESHOLD: {} ", coupon.getClass().getName(), coupon.getBenefit(),
					coupon.getThresholdAmount());
		}
		log.info("-----------------------------------");
		log.info("-----------------------------------");
		log.info("TOTAL PRICE WITHOUT DISCOUNTS: {}\tDISCOUNT CAMPAIGN: {}\tDISCOUNT COUPON: {}", totalSum(),
				getCampaignDiscount(), getCouponDiscount());

		log.info("-----------------------------------");
		log.info("-----------------------------------");
		log.info("TOTAL PRICE: {}\tDELIVERY COST: {}", totalSumAfterDiscounts(), getDeliveryCost());
		log.info("*STRATEGY INFORMATION: {}", deliveryCostCalculator.getStrategyInformation());

		// total amount and delivery cost
	}

	private Object totalSumAfterDiscounts() {
		double lastSum = totalSum() - getCampaignDiscount() - getCouponDiscount();
		return lastSum > 0 ? lastSum : 0;
	}

}
