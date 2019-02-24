package com.mcy.cart;

import com.mcy.category.CategoryParentable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
	private double oldSum;
	private double discountSum;
	private CategoryParentable category;
	private String type;
	private double benefit;
	private int minimumItemCount;
}
