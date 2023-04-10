package com.example.demo.repositories;

import com.example.demo.domain.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Integer> {
}
