package com.cufos.bussiness.courses;

import com.cufos.model.CourseModel;

import java.util.List;
import java.util.Optional;

public interface coursesDao {
  List<CourseModel> getAllCourses();

  CourseModel getCourseById(Long id);

  void createCourse(CourseModel course);

  void deleteCourse(Long id);

  void createCourseUser(Long id, CourseModel course);

  void updateCourse(Long id, CourseModel course);
}
