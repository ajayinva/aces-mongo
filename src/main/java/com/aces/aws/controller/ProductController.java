/**
 * 
 */
package com.aces.aws.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.service.ProductService;
/**
 * @author aagarwal
 *
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController  extends BaseController {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	/**
	 * 
	 */
	@Autowired
	private ProductService productService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/viewAll")
	public Map<String,Object> viewAll(){
		LOGGER.debug("Inside viewAll() for ProductController");
		Map<String,Object> result = new HashMap<>();
		result.put("productList",productService.getProductList());
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/view")
	public Map<String,Object> view(Integer productId){
		LOGGER.debug("Inside view() for ProductController");
		Map<String,Object> result = new HashMap<>();
		result.put("product",productService.getProduct(productId));
		return result;
	}
}
