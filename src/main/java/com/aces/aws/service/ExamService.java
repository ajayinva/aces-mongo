/**
 * 
 */
package com.aces.aws.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aces.aws.domain.ExamQuestionDto;
import com.aces.aws.entity.Exam;
import com.aces.aws.entity.ExamQuestion;
import com.aces.aws.entity.Question;
import com.aces.aws.entity.QuestionOption;
import com.aces.aws.entity.User;
import com.aces.aws.repositories.ExamRepository;

/**
 * @author aagarwal
 *
 */
@Service
@Transactional
public class ExamService {	
	@Autowired
	private ExamRepository examRepository;		
	@Autowired
	private QuestionService questionService;
	/**
	 * 
	 * @param id
	 * @param user
	 */
	public boolean deleteExam(String id, User user){
		boolean deleted = false;
		Exam exam = examRepository.findOne(id);
		if(exam.userName.equals(user.userName)){
			examRepository.delete(exam);
			deleted = true;
		}
		return deleted;
	}	
	/**
	 * 
	 * @param quiz
	 * @param user
	 * @return
	 */
	public Exam getExam(String id){
		return examRepository.findOne(id);
	}/**
	 * 
	 * @param quiz
	 * @param user
	 * @return
	 */
	public List<Exam> getExams(User user){
		return examRepository.findAllByUserName(user.userName);
	}
	
	/**
	 * 
	 * @param quiz
	 * @param user
	 * @return
	 */
	public Exam create(Exam exam, User user){		
		exam.userName = user.userName;
		return examRepository.save(exam);
	}
	/**
	 * 
	 * @param examId
	 * @param examQuestionid
	 * @param userId
	 * @return
	 */
	public ExamQuestion getExamQuestion(String examId, Long questionId){
		Exam exam = examRepository.findOne(examId);
		for(ExamQuestion question : exam.getQuestions()){
			if(question.questionId.equals(questionId)){
				return question;
			}
		}
		return null;
	}
	/**
	 * 
	 * @param examId
	 * @param examQuestionid
	 * @param userId
	 * @return
	 */
	public void saveExamQuestion(ExamQuestionDto examDto){
		List<Long> selectedOptions = examDto.getSelectedOptions();
		Exam exam = examRepository.findOne(examDto.getExamId());
		ExamQuestion examQuestion = null;
		for(ExamQuestion question:exam.getQuestions()){
			if(question.questionId.equals(examDto.getQuestionId())){
				examQuestion = question;
			}
		}		
		if(examQuestion!=null){
			examQuestion.correct = null;
			examQuestion.incorrect = null;
			if(examDto.isStarred()){
				examQuestion.starred = 1;
			} else {
				examQuestion.starred = null;
			}
			if(selectedOptions!=null && !selectedOptions.isEmpty()){
				Question question = questionService.getQuestion(examDto.getQuestionId());
				List<QuestionOption> options = question.options;									
				if(question.numberOfCorrectOptions.equals(1)){
					Long selectedOption = selectedOptions.get(0);
					for(QuestionOption option: options){
						if(option.correct && option.id.equals(selectedOption)){
							examQuestion.correct = 1;
							examQuestion.incorrect = null;							
						}
					}
					if(examQuestion.correct==null){												
						examQuestion.incorrect = 1;
					}
				} else {
					Set<QuestionOption> correctOptions = new HashSet<>();
					for(QuestionOption eachOption:options){
						if(eachOption.correct){
							correctOptions.add(eachOption);
						}
					}					
					if(selectedOptions.size()!=correctOptions.size()){						
						examQuestion.incorrect = 1;
					} else {
						for(Long id : selectedOptions){
							QuestionOption option = new QuestionOption(id);
							if(!correctOptions.contains(option)){
								examQuestion.incorrect = 1;								
								examQuestion.correct = null;
							}
						}
					}
					if(examQuestion.incorrect==null){
						examQuestion.correct = 1;					
					}
				}				
				if(selectedOptions.size()==1){
					examQuestion.selectedOption1 = selectedOptions.get(0);				
				}	
				if(selectedOptions.size()==2){									
					examQuestion.selectedOption1 = selectedOptions.get(0);	
					examQuestion.selectedOption2 = selectedOptions.get(1);	
				}	
				if(selectedOptions.size()>=3){
					examQuestion.selectedOption1 = selectedOptions.get(0);	
					examQuestion.selectedOption2 = selectedOptions.get(1);
					examQuestion.selectedOption3 = selectedOptions.get(2);	
				}						
			} else{				
				examQuestion.incorrect = 1;	
			}
		}
		examRepository.save(exam);
	}
}
