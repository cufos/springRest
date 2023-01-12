package com.cufos.bussiness;

import com.cufos.model.ExamModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface examBO {
  List<ExamModel> getExams();

  ExamModel getExamById(Long id);

  void createExam(ExamModel exam);

  Optional<Set<ExamModel>> getNoteExam(int note);

  void deleteExam(Long id);
}
