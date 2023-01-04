package com.cufos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class CourseModel {

  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Getter
  @Setter
  @Column(name = "name")
  private String name;

  @Getter
  @Setter
  @Column(name = "description")
  private String description;

  @ManyToMany(cascade =  {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST},
    fetch = FetchType.LAZY)
//  @JsonIgnore
  @JoinTable(name = "courses_users",
          joinColumns = @JoinColumn(name = "course_id"),
          inverseJoinColumns = @JoinColumn(name = "users_id"))
  private Set<UserModel> users = new LinkedHashSet<>();

  public Set<UserModel> getUsers(){return users; }

  public void setUsers(Set<UserModel> users) {this.users = users; }
}
