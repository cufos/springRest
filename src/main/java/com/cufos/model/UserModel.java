package com.cufos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
  })
public class UserModel {
  @Id
  @Setter
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Setter
  @Getter
  @NotBlank
  @Size(max = 20)
  private String username;
  @Setter
  @Getter
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  @Setter
  @Getter
  @NotBlank
  @Size(max = 120)
  private String password;

  @Getter
  @Setter
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleModel> roles = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST}, mappedBy = "users")
  private Set<CourseModel> courses = new LinkedHashSet<>();


  @Getter
  @Setter
  @OneToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "user_exams",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "exam_id"))
  private Set<ExamModel> exams = new HashSet<>();


  public Set<CourseModel> getCourses() {
    return courses;
  }

  public void setCourses(Set<CourseModel> courses) {
    this.courses = courses;
  }

  public UserModel() {
  }

  public UserModel(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

}