package com.hanye.info.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
