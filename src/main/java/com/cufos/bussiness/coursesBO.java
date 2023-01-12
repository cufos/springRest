package com.cufos.bussiness;

import com.cufos.model.CourseModel;

import java.util.List;

public interface coursesBO {
  List<CourseModel> getAllCourses();

  CourseModel getCourseById(Long id);

  void createCourse(CourseModel course);

  void deleteCourse(Long id);

  void createCourseUser(Long id, CourseModel course);

  void updateCourse(Long id, CourseModel course);
}
