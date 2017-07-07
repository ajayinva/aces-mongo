package com.aces.aws.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.Exam;

/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface ExamRepository extends CrudRepository<Exam, String> {
    List<Exam> findAllByUserName(String userName);
}
