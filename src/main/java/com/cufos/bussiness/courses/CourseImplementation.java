package com.cufos.bussiness.courses;

import com.cufos.exception.ResourceNotFoundException;
import com.cufos.model.CourseModel;
import com.cufos.model.UserModel;
import com.cufos.repository.CourseRepository;
import com.cufos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseImplementation implements coursesDao {

  private final CourseRepository courseRepository;

  private final UserRepository userRepository;

  public CourseImplementation(CourseRepository courseRepository, UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<CourseModel> getAllCourses() {
    List<CourseModel> courseArrayList = new ArrayList<>(courseRepository.findAll());

    return  courseArrayList;
  }

  @Override
  public Optional<CourseModel> getCourseById(Long id) {
    Optional<CourseModel> _course = courseRepository.findById(id);
    if (!courseRepository.existsById(id)){
      throw new ResourceNotFoundException("Course with [%id] not found".formatted(id));
    }
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
    CourseModel _course = courseRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Not found Course with [%id]".formatted(id)));
    _course.setName(course.getName());
    _course.setDescription(course.getDescription());
  }


}
