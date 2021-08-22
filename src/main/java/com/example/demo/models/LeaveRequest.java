package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long leaveId;

    @Column
    private Date leaveStartDate;

    @Column
    private Date leaveEndDate;

    @Column
    private String requestStatus;

    @Column
    private String leaveType;

    @ManyToOne
    private Employee employee;
}
