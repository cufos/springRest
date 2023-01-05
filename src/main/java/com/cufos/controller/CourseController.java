package com.cufos.controller;

import com.cufos.model.CourseModel;
import com.cufos.model.UserModel;
import com.cufos.repository.CourseRepository;
import com.cufos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {
  @Autowired
  CourseRepository courseRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/course")
  public ResponseEntity<List<CourseModel>> getCourses() {
    List<CourseModel> courseArrayList = new ArrayList<>(courseRepository.findAll());

    if (courseArrayList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(courseArrayList, HttpStatus.OK);

  }

  @GetMapping("/course/{id}")
  public ResponseEntity<Optional> getCourse(@PathVariable("id") Long id) {
    Optional<CourseModel> _course = courseRepository.findById(id);
    if (_course.isPresent()) {
      return new ResponseEntity<Optional>(_course, HttpStatus.OK);
    }
    else
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @PostMapping("/course")
  public ResponseEntity<CourseModel> createCourses (@RequestBody CourseModel course){
    CourseModel _course = courseRepository.save(course);
    return new ResponseEntity<>(_course, HttpStatus.CREATED);
  }

  @PostMapping("/users/{id}/course")
  public ResponseEntity<CourseModel> CreateCourseUser(@PathVariable Long id ,@RequestBody CourseModel course) {
    UserModel user = userRepository.getReferenceById(id);
    Set<UserModel> userSet = new HashSet<>();
    userSet.add(user);
    course.setUsers(userSet);
    CourseModel _course = courseRepository.save(course);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

//  @PutMapping("/course/{id}")
//  public ResponseEntity<CourseModel> updateRoles(@PathVariable("id") long id, @RequestBody CourseModel course) {
//    CourseModel _course = courseRepository.findById(id)
//      .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));
//    _course.setName(course.getName());
//    return new ResponseEntity<>(courseRepository.save(_course), HttpStatus.OK);
//  }

  @DeleteMapping("/course/{id}")
  public  ResponseEntity<HttpStatus> deleteCourse(@PathVariable long id){
    courseRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
