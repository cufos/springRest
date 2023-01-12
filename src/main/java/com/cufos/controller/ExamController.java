package com.cufos.controller;

import com.cufos.model.ExamModel;
import com.cufos.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ExamController {
  @Autowired
  ExamRepository examRepository;

  @PostMapping("/exam")
  public ResponseEntity<ExamModel> createExam (@RequestBody ExamModel exam)  {
    ExamModel _exam = examRepository.save(exam);
    return new ResponseEntity<>(_exam, HttpStatus.CREATED);
  }

  @GetMapping("/exam/users")
  public ResponseEntity<List<ExamModel>> getExams(){
    List<ExamModel> examArrayList = new ArrayList<>(examRepository.findAll());

    if (examArrayList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(examArrayList, HttpStatus.OK);
  }

  @GetMapping("/exam/{id}")
  public ResponseEntity<ExamModel> getExam(@PathVariable Long id){
    ExamModel _exam = examRepository.getReferenceById(id);
    return new ResponseEntity<ExamModel>(_exam,HttpStatus.OK);
  }

  @GetMapping("/exam/{note}") //UTILIZZA USER SERVICE
  public ResponseEntity<?> getExamByNote (@PathVariable("note") int val){
    Optional<Set<ExamModel>> _exam = examRepository.findByValutazione(val);

    return new ResponseEntity<>(_exam, HttpStatus.OK);
  }

  @DeleteMapping("/exam/{id}")
  public  ResponseEntity<HttpStatus> deleteExam(@PathVariable long id){
    examRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
