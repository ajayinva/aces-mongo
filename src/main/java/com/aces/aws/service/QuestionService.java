/**
 * 
 */
package com.aces.aws.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aces.aws.cache.QuestionCache;
import com.aces.aws.entity.Question;

/**
 * @author aagarwal
 *
 */
@Service
@Transactional
public class QuestionService {
	/**
	 * 
	 */
    @Autowired
    private QuestionCache questionCache;
   	/**
	 * 
	 * @param dto
	 */
	public Question getQuestion(Long id){		
		return questionCache.getById(id);
	}
	/**
	 * 
	 * @param sectionId
	 * @return
	 */
	public Set<Question> getQuestionsBySection(long sectionId){
		return questionCache.getQuestionsBySection(sectionId);
	}
}
