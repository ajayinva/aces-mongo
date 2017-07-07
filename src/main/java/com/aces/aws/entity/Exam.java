package com.aces.aws.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author aagarwal
 *
 */
@Document(collection="exams")
public class Exam implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4938338226309448429L;
	
	@Id
    public String id;
        
	public Integer totalQuestions = 0;
        
	public Integer correct;
        
	public Integer incorrect;
            
	public Integer status;

	public String userName;
	
	public Integer type;
	    
	public LocalDateTime creationDate;
    	
	public List<ExamQuestion> questions = new ArrayList<>();
		
	public Long sectionId;
	
	@Transient
	public String name;
	
	/**
	 * 
	 * @return
	 */
	public Long getCreationDateToDisplay(){
		if(creationDate!=null){
			return creationDate.toInstant(ZoneOffset.UTC).toEpochMilli();
		}
		return null;
	}
    
	/**
	 * @return the questions
	 */
	public List<ExamQuestion> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<ExamQuestion> questions) {
		this.questions = questions;
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
        Exam that = (Exam) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
