package com.example.demo.service.impl;

import com.example.demo.enums.AttendanceStatus;
import com.example.demo.models.Attendance;
import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import com.example.demo.models.Salary;
import com.example.demo.repositories.AttendanceRepository;
import com.example.demo.repositories.LeaveRequestRepository;
import com.example.demo.repositories.SalaryRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    LeaveRequestRepository leaveRequestRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    SalaryRepository salaryRepository;


    @Override
    public void requestForLeave(LeaveRequest leaveRequest) {
        this.leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public boolean markAttendance(Employee employee) {
        LocalTime reportTime= LocalTime.parse("08:00:00.000");
        LocalTime closingTime= LocalTime.parse("10:00:00.000");
        LocalDate localDate= LocalDate.now();
        LocalTime localTime= LocalTime.now();
        boolean status;
        Optional<Attendance> attendance = attendanceRepository
                .findAttendanceByEmployee_EmployeeIdAndLocalDate(employee.getEmployeeId(), localDate);
        if (attendance.isEmpty()) {
            Attendance attendance1 = new Attendance();
            attendance1.setEmployee(employee);
            if (localTime.isAfter(reportTime) && localTime.isAfter(closingTime)) {
                attendance1.setAttendanceStatus(AttendanceStatus.ABSENT);
            } else if (localTime.isBefore(reportTime)){
                attendance1.setAttendanceStatus(AttendanceStatus.PRESENT);
            } else {
                attendance1.setAttendanceStatus(AttendanceStatus.LATE);
            }
            status = true;
            attendance1.setLocalDate(localDate);
            attendance1.setLocalTime(localTime);
            attendanceRepository.save(attendance1);
        } else {
            status = false;
            System.out.println("marked already");
        }
        return status;
    }

    @Override
    public List<Salary> getEmployeeSalaryHistory(long employeeId) {
        return salaryRepository.findSalaryByEmployee_EmployeeId(employeeId);
    }
}
