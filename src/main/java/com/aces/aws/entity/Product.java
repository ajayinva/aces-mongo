package com.aces.aws.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * @author aagarwal
 *
 */
@Document(collection = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 6859182161769647497L;
	
    @Id
    public Long id;
     
    public String name;

    public String description;    

    public List<ProductPlan> plans = new ArrayList<>();	    

    public List<String> features = new ArrayList<>();
}
