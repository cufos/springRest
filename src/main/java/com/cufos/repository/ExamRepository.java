package com.cufos.repository;

import com.cufos.model.ExamModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ExamRepository extends JpaRepository<ExamModel,Long> {
  Optional<Set<ExamModel>> findByValutazione(int valutazione);
}
