/**
 * 
 */
package com.aces.aws.controller;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.ExamDto;
import com.aces.aws.domain.ExamQuestionDto;
import com.aces.aws.entity.Exam;
import com.aces.aws.entity.ExamQuestion;
import com.aces.aws.entity.Product;
import com.aces.aws.entity.ProductPlan;
import com.aces.aws.entity.Question;
import com.aces.aws.infra.GlobalConstants;
import com.aces.aws.infra.GlobalFunctions;
import com.aces.aws.service.ExamService;
import com.aces.aws.service.ProductService;
import com.aces.aws.service.QuestionService;
/**
 * @author aagarwal
 *
 */
@RestController
public class ExamController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 
	 * @param exam
	 * @param newQuestion
	 * @return
	 */
	private boolean isQuestionAlreadyAdded(Exam exam, ExamQuestion newQuestion){				
		for(ExamQuestion question : exam.getQuestions()){
			if(question.equals(newQuestion)){
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param exam
	 * @param questionNumber
	 */
	private void populateExamQuestion(Exam exam, Long questionId){
		ExamQuestion currentExamQuestion = new ExamQuestion();							
		currentExamQuestion.questionId = questionId;		
		if(!isQuestionAlreadyAdded(exam, currentExamQuestion)){
			exam.getQuestions().add(currentExamQuestion);
		}		
	}	
	/**
	 * 
	 * @param exam
	 * @param examDto
	 */
	private void createQuestionSet(Exam exam, ExamDto examDto){
		if(examDto.getSelectedExam()!=null){			
			examDto.setSelectedSection(examDto.getSelectedExam());
			exam.type = GlobalConstants.EXAM_TYPE_CERTIFICATION;
		} else {
			exam.type = GlobalConstants.EXAM_TYPE_QUIZ;
		}
		exam.sectionId = examDto.getSelectedSection();
		if(examDto.getQuestionType()== GlobalConstants.QUESTION_TYPE_ALL){			
			Long section = examDto.getSelectedSection();
			Set<Question> sectionQuestions = questionService.getQuestionsBySection(section);
			if(sectionQuestions!=null && !sectionQuestions.isEmpty()){
				for(Question question : sectionQuestions){
					this.populateExamQuestion(exam, question.id);
				}
			}				
		} else if(examDto.getQuestionType()== GlobalConstants.QUESTION_TYPE_INCORRECT) {						
			for(Exam previousExam : examService.getExams(this.getCurrentUser())){
				if(previousExam.status !=null && previousExam.status == GlobalConstants.EXAM_STATUS_COMPLETED){
					List<ExamQuestion> examQuestions = previousExam.getQuestions();
					for(ExamQuestion examQuestion : examQuestions){
						if(examQuestion.incorrect !=null){
							Question question = questionService.getQuestion(examQuestion.questionId);
							if(examDto.getSelectedSection().equals(question.sectionId)){
								this.populateExamQuestion(exam, question.id);
							}
						}
					}
				}			
			}			
		} else if(examDto.getQuestionType()== GlobalConstants.QUESTION_TYPE_STARRED) {						
			for(Exam previousExam : examService.getExams(this.getCurrentUser())){
				if(previousExam.status!=null && previousExam.status == GlobalConstants.EXAM_STATUS_COMPLETED){
					List<ExamQuestion> examQuestions = previousExam.getQuestions();
					for(ExamQuestion examQuestion : examQuestions){
						if(examQuestion.starred !=null){
							Question question = questionService.getQuestion(examQuestion.questionId);
							if(examDto.getSelectedSection().equals(question.sectionId)){
								this.populateExamQuestion(exam,question.id);
							}
						}
					}
				}			
			}			
		}
		if(!exam.getQuestions().isEmpty()){
			this.createQuestionLinkedList(exam);
		}
	}
	/**
	 * 
	 * @param exam
	 */
	private void createQuestionLinkedList(Exam exam){
		if(exam.type.equals(GlobalConstants.EXAM_TYPE_CERTIFICATION)){
			Collections.shuffle(exam.getQuestions());
		}
		int number = 1;
		for(ExamQuestion question:exam.getQuestions()){
			question.questionNumber = number++;
		}
		ExamQuestion previousQuestion = null;
		ExamQuestion currentQuestion;			
		Iterator<ExamQuestion> it = exam.getQuestions().iterator();
		boolean hasNext;
		do{		
			if(previousQuestion==null) {
				previousQuestion = it.next();
			}
			if(it.hasNext()){
				hasNext = true;
				currentQuestion = it.next();
				previousQuestion.nextQuestionId = currentQuestion.questionId;
				currentQuestion.previousQuestionId = previousQuestion.questionId;
				previousQuestion = currentQuestion;
			} else {
				hasNext = false;						
			}
		} while (hasNext);
	}	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/createExamSave")
	public Map<String,Object> save(@RequestBody @Valid ExamDto examDto){
		Map<String,Object> result = new HashMap<>();		
		Exam exam = new Exam();	
		exam.creationDate = LocalDateTime.now(Clock.systemUTC());				
		this.createQuestionSet(exam, examDto);
		if(!exam.getQuestions().isEmpty()){
			exam.totalQuestions = exam.getQuestions().size();
			exam.status = GlobalConstants.EXAM_STATUS_IN_PROGRESS;
			Exam createdExam = examService.create(exam, GlobalFunctions.getCurrentUser());
			ExamQuestion firstExamQuestion = createdExam.getQuestions().iterator().next();
			result.put("totalQuestions", exam.totalQuestions);
			result.put("examId", createdExam.id);
			result.put("examQuestionDto", getExamQuestionDto(firstExamQuestion, createdExam.id));
			result.put("question", questionService.getQuestion(firstExamQuestion.questionId));
		} else {
			Map<String, String> messages = new HashMap<>();
			messages.put("noQuestionsFound", messageSource.getMessage("noQuestionsFound", null,null));
			result.put("error", true);
			result.put("messages", messages);
			
		}
		return result;
	}
	/**
	 * 
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "/p/examSummary")
	public Map<String,Object> examSummary(String examId){
		Map<String,Object> result = new HashMap<>();
		Exam exam = examService.getExam(examId);
		result.put("examId", exam.id);		
		result.put("correct", exam.correct);
		result.put("incorrect", exam.incorrect);
		result.put("total", exam.totalQuestions);
		result.put("status", exam.status);
		result.put("examName", productService.getSection(exam.sectionId).name);
		if(exam.correct!=null){
			result.put(
				"percentage"
			, (new Double(exam.correct)/new Double(exam.totalQuestions))*100
			);
		}
		int totalAnswered = 0;
		for(ExamQuestion question:exam.getQuestions()){
			if(StringUtils.isEmpty(question.previousQuestionId==null)){
				result.put("examQuestion", question);
				result.put("examQuestionDto", getExamQuestionDto(question,exam.id));
				result.put("question", questionService.getQuestion(question.questionId));
			}
			if(question.correct!=null || question.incorrect!=null){
				++totalAnswered;
			}
		}
		result.put("totalQuestions", exam.totalQuestions);
		result.put("totalAnswered", totalAnswered);
		result.put("totalSkipped", exam.totalQuestions - totalAnswered);
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/viewQuestion")
	public Map<String,Object> examQuestion(String examId, Long questionId){
		Map<String,Object> result = new HashMap<>();
		ExamQuestion examQuestion = examService.getExamQuestion(examId, questionId);		
		result.put("examQuestion", examQuestion);
		result.put("question", questionService.getQuestion(examQuestion.questionId));
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/createExamInit")
	public Map<String,Object> init(Integer planId){
		LOGGER.debug("Inside init() for ExamController");
		Map<String,Object> result = new HashMap<>();				
		ProductPlan productPlan = productService.getProductPlan(planId);
		Product product = productService.getProduct(productPlan.productId);
		result.put("planId",planId);
		result.put("productName",product.name);
		result.put("sections", productPlan.sections);
		result.put("finalExams", productPlan.exams);	
		ExamDto exam = new ExamDto();
		result.put("exam",exam);
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/question")
	public Map<String,Object> saveAndGetQuestion(@RequestBody @Valid ExamQuestionDto examDto){
		Map<String,Object> result = new HashMap<>();
		examService.saveExamQuestion(examDto);	
		Long questionId = examDto.getNextQuestionId();
		if(examDto.isPrevious()){
			questionId = examDto.getPreviousQuestionId();
		}
		if(questionId!=null){
			ExamQuestion nextQuestion = examService.getExamQuestion(examDto.getExamId(), questionId);					
			result.put("examQuestionDto", getExamQuestionDto(nextQuestion, examDto.getExamId()));
			result.put("question", questionService.getQuestion(nextQuestion.questionId));
		}
		result.put("examId", examDto.getExamId());
		return result;
	}
	/**
	 * 
	 * @param examQuestion
	 * @return
	 */
	private ExamQuestionDto getExamQuestionDto(ExamQuestion examQuestion, String examId){
		ExamQuestionDto examQuestionDto = new ExamQuestionDto();
		//examQuestionDto.setId(examQuestion.getId());
		examQuestionDto.setExamId(examId);
		examQuestionDto.setNextQuestionId(examQuestion.nextQuestionId);		
		examQuestionDto.setQuestionId(examQuestion.questionId);
		examQuestionDto.setPreviousQuestionId(examQuestion.previousQuestionId);
		if(examQuestion.selectedOption1!=null){
			examQuestionDto.getSelectedOptions().add(examQuestion.selectedOption1);
		}
		if(examQuestion.selectedOption2!=null){
			examQuestionDto.getSelectedOptions().add(examQuestion.selectedOption2);
		}
		if(examQuestion.selectedOption3!=null){
			examQuestionDto.getSelectedOptions().add(examQuestion.selectedOption3);
		}
		examQuestionDto.setQuestionNumber(examQuestion.questionNumber);
		if(examQuestion.starred!=null){
			examQuestionDto.setStarred(true);
		}		
		return examQuestionDto;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/submitExam")
	public Map<String,Object> submitExam(String examId){		
		String userName = this.getCurrentUser().userName;
		Exam exam = examService.getExam(examId);
		if(exam.userName.equals(userName)){
			int totalCorrect = 0;
			int totalInCorrect = 0;
			for(ExamQuestion question : exam.getQuestions()){
				if(question.correct!=null){
					++totalCorrect;
				}
				else {
					++totalInCorrect;
				}
			}
			exam.correct = totalCorrect;
			exam.incorrect = totalInCorrect;
			exam.status = GlobalConstants.EXAM_STATUS_COMPLETED;
			examService.create(exam, getCurrentUser());
		}				
		return examSummary(examId);
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/deleteExam")
	public Map<String,Object> deleteExam(String examId){
		Map<String,Object> result = new HashMap<>();
		result.put("deleted", examService.deleteExam(examId, this.getCurrentUser()));
		return result;
	}
}
