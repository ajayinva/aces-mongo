package com.aces.aws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.Section;
/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {
}
