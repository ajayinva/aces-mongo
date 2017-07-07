package com.aces.aws.entity;
import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author aagarwal
 *
 */
@Document(collection = "sections")
public class Section implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -786950369598975948L;	
	@Id
	public Long id;
    
	public String name;
    
	public String type;
	
	public String details;
	/**
	 * 
	 */
	public Section (Long id, String name, String type, String details){
		this.id = id;		
		this.name = name;
		this.type = type;
		this.details = details;
	}
}
