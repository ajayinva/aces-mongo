/**
 * 
 */
package com.aces.aws.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.aces.aws.entity.Product;

import com.aces.aws.entity.ProductPlan;

import com.aces.aws.entity.Section;
import com.aces.aws.repositories.ProductRepository;
import com.aces.aws.repositories.SectionRepository;
/**
 * @author aagarwal
 *
 */
@Component
public class ProductCache {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCache.class);
	
    @Autowired
    private ProductRepository productRepository;
   
    @Autowired
    private SectionRepository sectionRepository;
    
	private List<Product> productList = new ArrayList<>();

	private Map<Long,Product> productMapByID = new HashMap<>();
	
	private Map<Long,ProductPlan> productPlanMapByID = new HashMap<>();
	
	private Map<Long,Section> sectionMapById = new HashMap<>();
	/**
	 * 
	 */
	@PostConstruct
	public void init(){		
		LOGGER.debug("Loading Product Sections....");
		Iterable<Section> sections = sectionRepository.findAll();
		sections.forEach(section -> {	
			sectionMapById.put(section.id, section);
		});			
		LOGGER.debug("Loading Products....");
		Iterable<Product> products = productRepository.findAll();		
		products.forEach(product -> {
			productList.add(product);
			productMapByID.put(product.id, product);
			product.plans.forEach(plan->{
				productPlanMapByID.put(plan.id, plan);
				plan.sectionIds.forEach(sectionId->{
					plan.sections.add(sectionMapById.get(sectionId));
				});
				plan.examIds.forEach(examId->{
					plan.exams.add(sectionMapById.get(examId));
				});
			});
		});
	}
	/**
	 * @return the productList
	 */
	public List<Product> getProductList() {
		return productList;
	}
	/**
	 * 
	 * @return
	 */
	public Product getProduct(Long id){
		return productMapByID.get(id);
	}
	/**
	 * 
	 * @return
	 */
	public ProductPlan getProductPlan(Long id){
		return productPlanMapByID.get(id);
	}
	/**
	 * 
	 * @return
	 */
	public Section getSection(Long id){
		return sectionMapById.get(id);
	}
}
