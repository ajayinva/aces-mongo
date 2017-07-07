package com.aces.aws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.ExamQuestion;

/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface ExamQuestionRepository extends CrudRepository<ExamQuestion, Long> {

}
