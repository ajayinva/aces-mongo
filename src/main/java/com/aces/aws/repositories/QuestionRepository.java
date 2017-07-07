package com.aces.aws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.Question;
/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
}
