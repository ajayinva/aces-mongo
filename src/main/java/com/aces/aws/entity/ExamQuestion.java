package com.aces.aws.entity;

import java.io.Serializable;
/**
 * 
 * @author aagarwal
 *
 */
public class ExamQuestion implements Serializable, Comparable<ExamQuestion>  {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -786950369598975948L;
	
	public Long questionId;

	public Integer correct;

	public Integer incorrect;

	public Integer starred;
    
	public Long nextQuestionId;
    
	public Long previousQuestionId;
    
	public Long selectedOption1;
    
	public Long selectedOption2;
    
	public Long selectedOption3;
    
	public Integer questionNumber;
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
        ExamQuestion that = (ExamQuestion) o;
        return this.questionId.equals(that.questionId);
	}
    /**
     * 
     */
	@Override
	public int hashCode() {
		return questionId.hashCode();
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ExamQuestion o) {
		return questionNumber.compareTo(o.questionNumber);
	}
}
