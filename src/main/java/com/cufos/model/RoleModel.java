package com.cufos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "roles")
public class RoleModel {
  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private RoleEn name;

  public RoleModel(){}
}
