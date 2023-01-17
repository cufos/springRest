package com.cufos.bussiness.impl;

import com.cufos.bussiness.coursesBO;
import com.cufos.exception.RequestValidationException;
import com.cufos.exception.ResourceNotFoundException;
import com.cufos.model.CourseModel;
import com.cufos.model.UserModel;
import com.cufos.repository.CourseRepository;
import com.cufos.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CourseBOImpl implements coursesBO {

  private final CourseRepository courseRepository;

  private final UserRepository userRepository;

  public CourseBOImpl(CourseRepository courseRepository, UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<CourseModel> getAllCourses() {
    List<CourseModel> courseArrayList = new ArrayList<>(courseRepository.findAll());

    return  courseArrayList;
  }

  @Override
  public CourseModel getCourseById(Long id) {

    if (!courseRepository.existsById(id)){
      throw new ResourceNotFoundException("Course with [%id] not found".formatted(id));
    }

    CourseModel _course = courseRepository.getReferenceById(id);

    return _course;
  }

  @Override
  public void createCourse(CourseModel course) {
    courseRepository.save(course);
  }

  @Override
  public void deleteCourse(Long id) {
    if (!courseRepository.existsById(id)){
      throw new ResourceNotFoundException("Course with [%id] not found".formatted(id));
    }
    courseRepository.deleteById(id);
  }

  @Override
  public void createCourseUser(Long id,CourseModel course) {
    if (!userRepository.existsById(id)){
      throw new ResourceNotFoundException("User with [%id] not found".formatted(id));
    }

    UserModel user = userRepository.getReferenceById(id);
    Set<UserModel> userSet = new HashSet<>();
    userSet.add(user);
    course.setUsers(userSet);
    CourseModel _course = courseRepository.save(course);
  }

  @Override
  public void updateCourse(Long id, CourseModel course) {
    CourseModel _course = getCourseById(id);
    boolean changes = false;

    if (course.getName() != null && !course.getName().equals(_course.getName())){
      _course.setName(course.getName());
      changes = true;
    }

    if (course.getDescription() != null && !course.getDescription().equals(_course.getDescription())){
      _course.setName(course.getName());
      changes = true;
    }

    if(!changes){
      throw new RequestValidationException("No data changes found");
    }

    courseRepository.save(_course);

  }

  @Override
  public CourseModel findByIdFile(Long id) {
    return courseRepository.findById(id).get();
  }

  @Override
  public void uploadFile(Long id, MultipartFile data) throws IOException {
    CourseModel _course = courseRepository.getReferenceById(id);
    _course.setData(data.getBytes());
    _course.setType(data.getContentType());
    courseRepository.save(_course);
  }


}
