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
import com.mcy.campaign.Campaign;
import com.mcy.campaign.CampaignAmountImpl;
import com.mcy.campaign.CampaignRateImpl;
import com.mcy.category.Category;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = McyShoppingCartApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
public class CampaignFactoryTest {
	CampaignFactory factory;

	@Mock
	BeanFactory beanFactoryMock;

	Category category;

	@Before
	public void setUp() throws Exception {
		category = new Category("title");
		when(beanFactoryMock.getBean(CampaignRateImpl.class, category, 0.0, 0))
				.thenReturn(new CampaignRateImpl(category, 0.0, 0));
		when(beanFactoryMock.getBean(CampaignAmountImpl.class, category, 0.0, 0))
				.thenReturn(new CampaignAmountImpl(category, 0.0, 0));
		factory = new CampaignFactory(beanFactoryMock);
	}

	@Test
	public void generateCampaign_whenDiscountTypeRate_thenReturnRateImpl() {
		Campaign campaign = factory.generateCampaign(category, 0.0, 0, DiscountType.RATE);
		assertTrue(campaign instanceof CampaignRateImpl);
	}

	@Test
	public void generateCampaign_whenDiscountTypeAmount_thenReturnAmountImpl() {
		Campaign campaign = factory.generateCampaign(category, 0.0, 0, DiscountType.AMOUNT);
		assertTrue(campaign instanceof CampaignAmountImpl);
	}
}
