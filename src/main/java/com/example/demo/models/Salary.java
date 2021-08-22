package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;

@Getter
@Setter
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long salaryId;
    private double amountPaid;
    private LocalDate datePaid;
    @Enumerated(EnumType.STRING)
    private Month monthInView;
    @ManyToOne
    private Employee employee;

}
