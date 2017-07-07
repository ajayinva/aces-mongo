package com.aces.aws.domain;

import java.io.Serializable;

import javax.validation.constraints.AssertFalse;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author aagarwal
 *
 */
public class ExamDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 214133255123184483L;
	
	private String id;
	
	private String name;
	
	private Integer totalQuestions;
		
	private Integer questionType = 1;
	
	private Long selectedExam;
	
	private Long selectedSection;
	/**
	 * 
	 * @return
	 */
	@AssertFalse
	@JsonIgnore
	public boolean isSectionOrExamRequired(){
		if(selectedSection==null && selectedExam==null){
			return true;
		}
		return false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the totalQuestions
	 */
	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	/**
	 * @param totalQuestions the totalQuestions to set
	 */
	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	/**
	 * @return the selectedExam
	 */
	public Long getSelectedExam() {
		return selectedExam;
	}

	/**
	 * @param selectedExam the selectedExam to set
	 */
	public void setSelectedExam(Long selectedExam) {
		this.selectedExam = selectedExam;
	}

	/**
	 * @return the questionType
	 */
	public Integer getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the selectedSection
	 */
	public Long getSelectedSection() {
		return selectedSection;
	}

	/**
	 * @param selectedSection the selectedSection to set
	 */
	public void setSelectedSection(Long selectedSection) {
		this.selectedSection = selectedSection;
	}
}
