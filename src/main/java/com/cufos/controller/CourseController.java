package com.cufos.controller;

import com.cufos.model.CourseModel;
import com.cufos.model.UserModel;
import com.cufos.repository.CourseRepository;
import com.cufos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


  @PostMapping("/course")
  public ResponseEntity<CourseModel> createCourses (@RequestBody CourseModel course){
    CourseModel _course = courseRepository.save(course);
    return new ResponseEntity<>(_course, HttpStatus.CREATED);
  }

  @PostMapping("/users/{userId}/courses")
  public ResponseEntity<?> insertCourse(@PathVariable("userId") long userId, @RequestBody CourseModel course){
    UserModel _user = userRepository.getReferenceById(userId);
    Set<UserModel> userSet = new HashSet<>();
    userSet.add(_user);
    course.setUsers(userSet);
    CourseModel _course = courseRepository.save(course);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


}
