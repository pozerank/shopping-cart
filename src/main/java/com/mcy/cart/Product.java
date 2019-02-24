package com.mcy.cart;

import com.mcy.category.CategoryParentable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private String title;
	private double price;
	private CategoryParentable category;
}
