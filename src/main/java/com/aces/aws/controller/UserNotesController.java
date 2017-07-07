/**
 * 
 */
package com.aces.aws.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.domain.Notes;
import com.aces.aws.entity.QuestionNote;
import com.aces.aws.service.QuestionService;
import com.aces.aws.service.UserService;
/**
 * @author aagarwal
 *
 */
@RestController
public class UserNotesController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
    /**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/p/mynotes/view")
	public Map<String,Object> view(Long questionId){
		Map<String,Object> result = new HashMap<>();
		QuestionNote notes = userService.getQuestionNotes(questionId);
		if(notes==null){
			Notes dto = new Notes();
			dto.setQuestionId(questionId);
			result.put("dto",dto);
		} else {
			result.put("dto",notes);
		}	
		result.put("question", questionService.getQuestion(questionId));
		return result;
	}
	/**
	 * 
	 * @param notes
	 * @return
	 */
	@RequestMapping(value = "/p/mynotes/save")
	public Map<String,Object> save(@Valid @RequestBody Notes notes){
		Map<String,Object> result = new HashMap<>();
		userService.saveNotes(notes);
		return result;
	}
}

