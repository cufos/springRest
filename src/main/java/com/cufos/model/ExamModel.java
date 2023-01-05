package com.cufos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "exam")
public class ExamModel {
  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Getter
  private double valutazione;

  @Setter
  @Getter
  private int day;

  @Setter
  @Getter
  private int month;

  @Setter
  @Getter
  private int year;

  @Setter
  @Getter
  private Date recovery;

  public ExamModel(){}
}
