package com.aces.aws.entity;

import java.io.Serializable;
/**
 * @author aagarwal
 *
 */
public class QuestionNote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2015709107292245193L;
	/**
	 * 
	 * @param questionId
	 * @param notes
	 */
	public QuestionNote(Long questionId, String notes){
		this.questionId = questionId;
		this.notes = notes;		
	}

	public Long questionId;
	
	public String notes;
}
