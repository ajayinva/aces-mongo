/**
 * 
 */
package com.aces.aws.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author aagarwal
 *
 */
public class ExamQuestionDto implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -786950369598975948L;

	private Long id;
	
	private String examId;
	
	private Long questionId;
	
    private Long nextQuestionId;
    
    private Long previousQuestionId;
    
    private List<Long> selectedOptions = new ArrayList<>();
    
    private boolean previous = false;
    
    private Integer questionNumber;
    
    private boolean starred;
    
	/**
	 * @return the starred
	 */
	public boolean isStarred() {
		return starred;
	}

	/**
	 * @param starred the starred to set
	 */
	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	/**
	 * @return the questionNumber
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * @param questionNumber the questionNumber to set
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * @return the previous
	 */
	public boolean isPrevious() {
		return previous;
	}

	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(boolean previous) {
		this.previous = previous;
	}
	/**
	 * @return the selectedOptions
	 */
	public List<Long> getSelectedOptions() {
		return selectedOptions;
	}

	/**
	 * @param selectedOptions the selectedOptions to set
	 */
	public void setSelectedOptions(List<Long> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	/**
	 * @return the examId
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * @param examId the examId to set
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the nextQuestionId
	 */
	public Long getNextQuestionId() {
		return nextQuestionId;
	}

	/**
	 * @param nextQuestionId the nextQuestionId to set
	 */
	public void setNextQuestionId(Long nextQuestionId) {
		this.nextQuestionId = nextQuestionId;
	}

	/**
	 * @return the previousQuestionId
	 */
	public Long getPreviousQuestionId() {
		return previousQuestionId;
	}

	/**
	 * @param previousQuestionId the previousQuestionId to set
	 */
	public void setPreviousQuestionId(Long previousQuestionId) {
		this.previousQuestionId = previousQuestionId;
	}
}
