package com.cufos.repository;

import com.cufos.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseModel, Long> {
  CourseModel getReferenceById(long id);

  boolean existsById(Long id);
}
