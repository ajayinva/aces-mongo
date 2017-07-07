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
@Document(collection = "questions")
public class Question implements Serializable, Comparable<Question> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4938338226309448429L;	
	@Id
    public Long id;
	
	public String code;
    
	public String text;

	public Long productId;
    
	public Long sectionId;
    
	public List<QuestionOption> options = new ArrayList<>();
	
    public Integer numberOfCorrectOptions = 1;
    
    /**
     * 
     * @param questionId
     * @param text
     * @param productId
     * @param sectionId
     * @param numberOfCorrectOptions
     */
    public Question (Long id, String code, String text, Long productId, Long sectionId, Integer numberOfCorrectOptions){
    	this.id = id;
    	this.code = code;
    	this.text = text;
    	this.productId = productId;
    	this.sectionId = sectionId;
    	this.numberOfCorrectOptions = numberOfCorrectOptions;
    }
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        Question that = (Question) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Question o) {
		return id.compareTo(o.id);
	}
}
