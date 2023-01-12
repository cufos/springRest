package com.cufos.bussiness.impl;

import com.cufos.bussiness.examBO;
import com.cufos.exception.ResourceNotFoundException;
import com.cufos.model.ExamModel;
import com.cufos.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExamBOImpl implements examBO {
  private final ExamRepository examRepository;

  public ExamBOImpl(ExamRepository examRepository) {
    this.examRepository = examRepository;
  }

  @Override
  public List<ExamModel> getExams() {
    List<ExamModel> examArrayList = new ArrayList<>(examRepository.findAll());

    return examArrayList;
  }

  @Override
  public ExamModel getExamById(Long id) {
    if(!examRepository.existsById(id)){
      throw new ResourceNotFoundException("Exam with [%id] not found".formatted(id));
    }

    ExamModel _exam = examRepository.getReferenceById(id);
    return _exam;
  }

  @Override
  public void createExam(ExamModel exam) {
    ExamModel _exam = examRepository.save(exam);
  }

  @Override
  public Optional<Set<ExamModel>> getNoteExam(int note) {
    Optional<Set<ExamModel>> _exam = examRepository.findByValutazione(note);
    return _exam;
  }

  @Override
  public void deleteExam(Long id) {
    if(!examRepository.existsById(id)){
      throw new ResourceNotFoundException("Exam with [%id] not found".formatted(id));
    }

    examRepository.deleteById(id);
  }
}
