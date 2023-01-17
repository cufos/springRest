package com.cufos.bussiness;

import com.cufos.model.CourseModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface coursesBO {
  List<CourseModel> getAllCourses();

  CourseModel getCourseById(Long id);

  void createCourse(CourseModel course);

  void deleteCourse(Long id);

  void createCourseUser(Long id, CourseModel course);

  void updateCourse(Long id, CourseModel course);

  public CourseModel findByIdFile(Long id);
  void uploadFile(Long id, MultipartFile data) throws IOException;
}
