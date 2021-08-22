package com.example.demo.models;

import com.example.demo.enums.AttendanceStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceId;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime localTime;
    @DateTimeFormat(pattern = "yyyy:mm:dd")
    private LocalDate localDate;
    @ManyToOne
    private Employee employee;

}
