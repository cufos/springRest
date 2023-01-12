package com.cufos.controller;

import com.cufos.bussiness.courses.CourseImplementation;
import com.cufos.model.CourseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {
  private final CourseImplementation courseImplementation;

  public CourseController(CourseImplementation courseImplementation) {
    this.courseImplementation = courseImplementation;
  }

  @GetMapping("/course")
  public ResponseEntity<List<CourseModel>> getCourses() {
    List<CourseModel> _course = courseImplementation.getAllCourses();
    return new ResponseEntity<>(_course, HttpStatus.OK);
  }

  @GetMapping("/course/{id}")
  public ResponseEntity<?> getCourse(@PathVariable("id") Long id) {
    CourseModel _course = courseImplementation.getCourseById(id);

      return new ResponseEntity<>(_course,HttpStatus.NO_CONTENT);
  }


  @PostMapping("/course")
  public ResponseEntity<?> createCourses (@RequestBody CourseModel course){
   courseImplementation.createCourse(course);
    return new ResponseEntity<>( HttpStatus.CREATED);
  }

  @PostMapping("/users/{id}/course")
  public ResponseEntity<CourseModel> CreateCourseUser(@PathVariable Long id ,@RequestBody CourseModel course) {
    courseImplementation.createCourseUser(id,course);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/course/{id}")
  public ResponseEntity<CourseModel> updateRoles(@PathVariable("id") long id, @RequestBody CourseModel course) {
   courseImplementation.updateCourse(id,course);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/course/{id}")
  public  ResponseEntity<HttpStatus> deleteCourse(@PathVariable long id){
   courseImplementation.deleteCourse(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
