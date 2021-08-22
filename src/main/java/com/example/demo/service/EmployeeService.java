package com.example.demo.service;

import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import com.example.demo.models.Salary;

import java.util.List;

public interface EmployeeService {
    void requestForLeave (LeaveRequest leaveRequest);
    boolean markAttendance (Employee employee);
    List<Salary> getEmployeeSalaryHistory(long employeeId);
}
