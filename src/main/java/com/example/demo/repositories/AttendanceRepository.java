package com.example.demo.repositories;

import com.example.demo.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
//    @Override
    Optional<Attendance> findAttendanceByEmployee_EmployeeIdAndLocalDate(Long aLong, LocalDate localDate);
}
