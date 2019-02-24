package com.mcy.cart.factories;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mcy.campaign.Campaign;
import com.mcy.campaign.CampaignAmountImpl;
import com.mcy.campaign.CampaignRateImpl;
import com.mcy.category.CategoryParentable;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CampaignFactory {

	private BeanFactory beanFactory;

	@Autowired
	public CampaignFactory(BeanFactory beanFactory) {
		super();
		this.beanFactory = beanFactory;
	}

	public Campaign generateCampaign(CategoryParentable category, double benefit, int minimumItemCount,
			DiscountType discountType) {
		Campaign result = null;

		switch (discountType) {
		case AMOUNT:
			result = beanFactory.getBean(CampaignAmountImpl.class, category, benefit, minimumItemCount);
			break;
		case RATE:
			result = beanFactory.getBean(CampaignRateImpl.class, category, benefit, minimumItemCount);
			break;
		default:
			log.error("UNDEFINED DISCOUNT TYPE");
			break;
		}
		return result;
	}

}
