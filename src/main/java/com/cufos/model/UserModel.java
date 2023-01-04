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
  @Size(max= 20)
  private String username;

  @Setter
  @Getter
  @NotBlank
  @Email
  @Size(max = 120)
  private String email;

  @Setter
  @Getter
  @NotBlank
  @Size(max = 100)
  private String password;

  @ManyToMany(cascade =  {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST},
    fetch = FetchType.LAZY)
  @JoinTable(name = "courses_users",
          joinColumns = @JoinColumn(name = "users_id"),
          inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<CourseModel> courses = new LinkedHashSet<>();

  public UserModel() {

  }

  public Set<CourseModel> getCourses(){return courses;}

  public void setCourses(Set<CourseModel> courses){this.courses = courses;}

}
