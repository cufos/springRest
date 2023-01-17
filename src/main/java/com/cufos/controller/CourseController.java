package com.cufos.controller;

import com.cufos.bussiness.impl.CourseBOImpl;
import com.cufos.model.CourseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {
  private final CourseBOImpl courseBOImpl;

  public CourseController(CourseBOImpl courseBOImpl) {
    this.courseBOImpl = courseBOImpl;
  }

  @GetMapping("/course")
  public ResponseEntity<List<CourseModel>> getCourses() {
    List<CourseModel> _course = courseBOImpl.getAllCourses();
    return new ResponseEntity<>(_course, HttpStatus.OK);
  }

  @GetMapping("/course/{id}")
  public ResponseEntity<?> getCourse(@PathVariable("id") Long id) {
    CourseModel _course = courseBOImpl.getCourseById(id);

      return new ResponseEntity<>(_course,HttpStatus.NO_CONTENT);
  }


  @PostMapping("/course")
  public ResponseEntity<?> createCourses (@RequestBody CourseModel course){
   courseBOImpl.createCourse(course);
    return new ResponseEntity<>( HttpStatus.CREATED);
  }

  @PostMapping("/users/{id}/course")
  public ResponseEntity<CourseModel> CreateCourseUser(@PathVariable Long id ,@RequestBody CourseModel course) {
    courseBOImpl.createCourseUser(id,course);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/course/{id}")
  public ResponseEntity<CourseModel> updateRoles(@PathVariable("id") long id, @RequestBody CourseModel course) {
   courseBOImpl.updateCourse(id,course);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/course/{id}")
  public  ResponseEntity<HttpStatus> deleteCourse(@PathVariable long id){
   courseBOImpl.deleteCourse(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/upload/{id}")
  public ResponseEntity<Map<String,String>> uploadFile(@PathVariable Long id ,@RequestParam("file") MultipartFile data) {
    try {
      courseBOImpl.uploadFile(id,data);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      Map<String,String> map = new HashMap<>();
      String message = "Non posso caricare il file: " + data.getOriginalFilename();
      map.put("Error",message);
      return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);

    }
  }
  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
    CourseModel _course = courseBOImpl.findByIdFile(id);
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + _course.getName() + "\"")
      .body(_course.getData());
  }

}
