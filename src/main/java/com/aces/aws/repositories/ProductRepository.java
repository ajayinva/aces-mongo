package com.aces.aws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aces.aws.entity.Product;
/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
