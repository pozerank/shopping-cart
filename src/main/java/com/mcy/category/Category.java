package com.mcy.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements CategoryParentable {

	private String title;
	private CategoryParentable parentCategory;

	@Override
	public void addParentCategory(CategoryParentable parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Category(String title) {
		this.title = title;
	}

}
