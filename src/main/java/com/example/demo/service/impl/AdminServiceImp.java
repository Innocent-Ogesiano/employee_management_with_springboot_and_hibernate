package com.example.demo.service.impl;

import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import com.example.demo.models.Salary;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.LeaveRequestRepository;
import com.example.demo.repositories.SalaryRepository;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private SalaryRepository salaryRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional< Employee > optional = employeeRepository.findById(id);
        Employee employee;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.employeeRepository.findAll(pageable);
    }

    @Override
    public List<LeaveRequest> viewAllLeaveRequest() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public LeaveRequest getLeaveRequestById(long id) {
        Optional<LeaveRequest> optional = leaveRequestRepository.findById(id);
        LeaveRequest leaveRequest;
        if (optional.isPresent()) {
            leaveRequest = optional.get();
        } else {
            throw new RuntimeException(" Leave Request not found for id :: " + id);
        }
        return leaveRequest;
    }

    @Override
    public void paySalaryByEmployeeId(long id) {
        int monthValue = Calendar.getInstance().get(Calendar.MONTH);
        Month monthInView = Month.of(monthValue);
        Optional<Salary> optionalSalary = salaryRepository.findSalaryByMonthInViewAndEmployee_EmployeeId(monthInView,id);
        if (optionalSalary.isEmpty()) {
            Employee employee = employeeRepository.getById(id);
            Salary salary = new Salary();
            salary.setEmployee(employee);
            salary.setAmountPaid(employee.getSalary());
            salary.setDatePaid(LocalDate.now());
            salary.setMonthInView(monthInView);
            salaryRepository.save(salary);
        } else {
            System.out.println("Salary already paid");
        }
    }

}
