package com.example.demo.repositories;

import com.example.demo.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
//    @Override
    Optional<Salary> findSalaryByMonthInViewAndEmployee_EmployeeId(Month month, Long aLong);
    List<Salary> findSalaryByEmployee_EmployeeId(long employeeId);
}
