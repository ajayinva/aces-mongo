package com.aces.aws.entity;

import java.io.Serializable;
/**
 * @author aagarwal
 *
 */
public class QuestionOption implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -786950369598975948L;
	
	public Long id;
	public String option;
	public boolean correct;
	
	public QuestionOption(){
		
	}
	
	/**
	 * 
	 * @param optionId
	 */
	public QuestionOption(Long id){
		this.id = id;
	}
	/**
	 * 
	 * @param optionId
	 * @param option
	 * @param correct
	 */
	public QuestionOption(Long id, String option, boolean correct){
		this.id = id;
		this.option = option;
		this.correct = correct;
	}
	/**
	 * 
	 */
	@Override
	public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        QuestionOption that = (QuestionOption) o;
        return this.id.equals(that.id);
	}
    /**
     * 
     */
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	/**
     * 
     */
	@Override
	public String toString() {
		return "id:"+id+"; option:"+option+"; correct:"+correct;
	}
}
