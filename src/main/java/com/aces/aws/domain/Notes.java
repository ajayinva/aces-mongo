/**
 * 
 */
package com.aces.aws.domain;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import com.aces.aws.infra.BaseDto;

/**
 * @author aagarwal
 *
 */
public class Notes extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4345965467270185462L;
	
	private Long questionId;
	
	private Long id;
	
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
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@NotEmpty
	@Size(max=4000)
	private String notes;
	
}
