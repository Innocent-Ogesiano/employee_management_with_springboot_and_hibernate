package com.example.demo.service;

import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize);
    List<LeaveRequest> viewAllLeaveRequest();
    LeaveRequest getLeaveRequestById(long id);
    void paySalaryByEmployeeId(long id);
//    List<LeaveRequest> viewAllPendingLeaveRequest(String requestStatus);
}
