/**
 * 
 */
package com.aces.aws.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aces.aws.entity.Question;
import com.aces.aws.repositories.QuestionRepository;
/**
 * @author aagarwal
 *
 */
@Component
public class QuestionCache {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionCache.class);
	
    @Autowired
    private QuestionRepository questionRepository;
    	
	private Map<Long,Set<Question>> questionMapBySection = new HashMap<>();
	
	private Map<Long,Question> questionMapById = new HashMap<>();
	/**
	 * 
	 */
	@PostConstruct
	public void init(){
		LOGGER.debug("Calling init on QuestionCache");
		Iterable<Question> questions = questionRepository.findAll();
		questions.forEach(question -> {			
			Set<Question> questionSet = questionMapBySection.get(question.sectionId);
			if(questionSet==null){
				questionSet = new TreeSet<>();
				questionMapBySection.put(question.sectionId, questionSet);
			}
			questionSet.add(question);
			questionMapById.put(question.id, question);
		});
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Question getById(Long id){
		return questionMapById.get(id);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Set<Question> getQuestionsBySection(Long sectionId){
		return questionMapBySection.get(sectionId);
	}
}
