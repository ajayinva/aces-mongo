/**
 * 
 */
package com.aces.aws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.MyProductButton;
import com.aces.aws.entity.Product;
import com.aces.aws.entity.ProductPlan;
import com.aces.aws.service.ProductService;
import com.aces.aws.service.UserService;

/**
 * @author aagarwal
 *
 */
@RestController
public class MyProductsController  extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MyProductsController.class);
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/myproducts", method = RequestMethod.GET)
	public Map<String,Object> myProducts(){
		LOGGER.debug("Inside myProducts() for MyProductsController");
		Map<String,Object> result = new HashMap<>();			
		List<ProductPlan> myPlans = userService.getUserProductPlans();
		Iterable<Product> allProducts = productService.getProductList();
		for(Product product : allProducts){			
			MyProductButton button = new MyProductButton();
			for(ProductPlan plan : product.plans){
				for(ProductPlan myPlan : myPlans){
					if(plan.equals(myPlan)){
						button.setEnter(true);
						button.setPlanId(myPlan.id);
						button.setTryNow(false);
						if(plan.free){
							button.setBuyNow(true);
						} else {
							button.setBuyNow(false);
						}
					}
				}
			}
			result.put(product.id+"", button);
		}
		result.put("allProducts",allProducts);	
		return result;
	}
}
