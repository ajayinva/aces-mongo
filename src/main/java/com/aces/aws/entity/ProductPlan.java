package com.aces.aws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Transient;
/**
 * @author aagarwal
 *
 */
public class ProductPlan implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -786950369598975948L;
	
	public Long id;
	
	public Long productId;
	
    public boolean free;
    
    public boolean best;
    
    public BigDecimal price;
    
    public Integer months;
    
    public List<String> features = new ArrayList<>();
    
    public List<Long> sectionIds = new ArrayList<>();
    
    public List<Long> examIds = new ArrayList<>();
    
    @Transient
    public List<Section> sections = new ArrayList<>();
    
    @Transient
    public List<Section> exams = new ArrayList<>();
}
