package com.cufos.controller;

import com.cufos.bussiness.impl.ExamBOImpl;
import com.cufos.model.ExamModel;
import com.cufos.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ExamController {
  private final ExamBOImpl examBOImpl;
  @Autowired
  ExamRepository examRepository;

  public ExamController(ExamBOImpl examBOImpl) {
    this.examBOImpl = examBOImpl;
  }

  @PostMapping("/exam")
  public ResponseEntity<?> createExam (@RequestBody ExamModel exam)  {
   examBOImpl.createExam(exam);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/exam")
  public ResponseEntity<List<ExamModel>> getExams(){
    List<ExamModel> exam = examBOImpl.getExams();
    if (exam.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(exam, HttpStatus.OK);
  }

  @GetMapping("/exam/{id}")
  public ResponseEntity<ExamModel> getExam(@PathVariable Long id){
    ExamModel _exam = examBOImpl.getExamById(id);
    return new ResponseEntity<>(_exam,HttpStatus.OK);
  }

  @GetMapping("/exam/{note}") //UTILIZZA USER SERVICE
  public ResponseEntity<?> getExamByNote (@PathVariable("note") int val){
    Optional<Set<ExamModel>> _exam = examBOImpl.getNoteExam(val);

    return new ResponseEntity<>(_exam, HttpStatus.OK);
  }

  @DeleteMapping("/exam/{id}")
  public  ResponseEntity<HttpStatus> deleteExam(@PathVariable long id){
    examBOImpl.deleteExam(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
