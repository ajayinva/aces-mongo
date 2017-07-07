/**
 * 
 */
package com.aces.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aces.aws.cache.ProductCache;
import com.aces.aws.entity.Product;
import com.aces.aws.entity.ProductPlan;
import com.aces.aws.entity.Section;

/**
 * @author aagarwal
 *
 */
@Service
@Transactional
public class ProductService {
	/**
	 * 
	 */
    @Autowired
    private ProductCache productCache;
   	/**
	 * 
	 * @param dto
	 */
	public Product getProduct(long id){		
		return productCache.getProduct(id);
	}
	/**
	 * 
	 * @param dto
	 */
	public ProductPlan getProductPlan(long id){		
		return productCache.getProductPlan(id);
	}
	/**
	 * 
	 * @param dto
	 */
	public Iterable<Product> getProductList(){		
		return productCache.getProductList();
	}
	/**
	 * 
	 * @param dto
	 */
	public Section getSection(long id){		
		return productCache.getSection(id);
	}
}
