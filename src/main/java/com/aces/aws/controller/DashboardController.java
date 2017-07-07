/**
 * 
 */
package com.aces.aws.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aces.aws.entity.Exam;
import com.aces.aws.entity.ProductPlan;
import com.aces.aws.infra.GlobalConstants;
import com.aces.aws.service.ExamService;
import com.aces.aws.service.ProductService;
import com.aces.aws.service.UserService;
/**
 * @author aagarwal
 *
 */
@RestController
public class DashboardController extends BaseController{
	@Autowired
	private ExamService examService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
    /**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mydashboard")
	public Map<String,Object> view(){
		Map<String,Object> result = new HashMap<>();	
		List<Exam> exams = examService.getExams(this.getCurrentUser());
		Set<Exam> finalExams = new HashSet<>();
		Set<Exam> quizes = new HashSet<>();
		for(Exam exam :exams){
			exam.name = productService.getSection(exam.sectionId).name;
			if(exam.type.equals(GlobalConstants.EXAM_TYPE_CERTIFICATION)){
				finalExams.add(exam);
			} else if(exam.type.equals(GlobalConstants.EXAM_TYPE_QUIZ)){
				quizes.add(exam);
			}
		}
		result.put("finalExams", finalExams);
		result.put("quizes", quizes);
		List<ProductPlan> myPlans = userService.getUserProductPlans();
		result.put("plan", myPlans.get(0));
		return result;
	}
}

