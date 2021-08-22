package com.example.demo.controller;

import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import com.example.demo.models.Salary;
import com.example.demo.repositories.LeaveRequestRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    AdminService adminService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @GetMapping("employeeDashboard")
    public String viewEmployeeHomePage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        Employee employee1 = adminService.getEmployeeById(employee.getEmployeeId());
        model.addAttribute("employee", employee1);
        return "employeeDashboard";
    }

    @GetMapping("/leaveRequest")
    public String showLeaveRequestForm (Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setRequestStatus("Pending");
//        model.addAttribute("employee", employee);
        model.addAttribute("leave", leave);
        return "leaveRequest";
    }

    @PostMapping("/leaveRequest")
    public String processLeaveRequest (@ModelAttribute ("leave") LeaveRequest leaveRequest) {
        this.employeeService.requestForLeave(leaveRequest);
        return "redirect:/employeeDashboard";
    }

    @GetMapping("viewEmployeeLeaveRequest")
    public String viewCurrentEmployeeLeaveRequest(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        model.addAttribute("listOfEmployeeLeaveRequest", leaveRequestRepository.findLeaveRequestByEmployee_EmployeeId(employee.getEmployeeId()));
        return "viewEmployeeLeaveRequest";
    }

    @GetMapping(value = "/saveAttendance")
    public String markAttendance(HttpSession session, Model model){
        Employee employee= (Employee) session.getAttribute("employee");
        boolean status = employeeService.markAttendance(employee);
        if (!status) {
            System.out.println("I'm inside mark attendance controller");
            model.addAttribute("marked", "Attendance already marked");
        }
        return "redirect:/employeeDashboard";
    }

    @GetMapping("viewSalaryHistory")
    public String viewSalaryHistory (Employee employee, HttpSession session, Model model) {
        employee = (Employee) session.getAttribute("employee");
        List<Salary> salaries = employeeService.getEmployeeSalaryHistory(employee.getEmployeeId());
        model.addAttribute("salaries", salaries);
        return "ViewSalaryHistory";
    }

}
