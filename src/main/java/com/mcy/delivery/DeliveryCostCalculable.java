package com.mcy.delivery;

import com.mcy.cart.ShoppingCart;

public interface DeliveryCostCalculable {
	double calculate(ShoppingCart cart);
}
