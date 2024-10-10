package com.iotiq.interview;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import com.iotiq.interview.controller.CategoryController;
import com.iotiq.interview.controller.MenuController;
import com.iotiq.interview.controller.messages.CategoryResponse;
import com.iotiq.interview.controller.messages.MenuResponse;

@SpringBootTest
class InterviewApplicationTests {

	@Autowired
	private MenuController menuController;

	@Autowired
	private CategoryController categoryController;

	@Test
	void contextLoads() {
		assertNotNull(menuController);
		assertNotNull(categoryController);
	}

	@Test
	void testMenuFilteringIntegration() {
		Page<MenuResponse> italianMenus = menuController.getAll("Italian", PageRequest.of(0, 10));
		assertNotNull(italianMenus);
		assertNotNull(italianMenus.getContent());
	}

	@Test
	void testCategoryFilteringIntegration() {
		List<CategoryResponse> dessertCategories = categoryController.getCategories("Dessert");
		assertNotNull(dessertCategories);
	}
}
