package com.example.demo.controller;

import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import com.example.demo.repositories.LeaveRequestRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private EmployeeService employeeService;

    // display list of employees
    @GetMapping("adminDashboard")
    public ModelAndView viewHomePage(ModelAndView model) {
        System.out.println("in view home page");
        return findPaginated(1);
    }
//    @GetMapping("adminDashboard")
//    public String viewHomePage(Model model) {
//        model.addAttribute("listEmployees", adminService.getAllEmployees());
//        return "adminDashboard";
//    }

    @GetMapping("viewPendingLeaveRequest")
    public String viewPendingLeaveRequest (Model model) {
        model.addAttribute("listOfPendingLeaveRequest", leaveRequestRepository.findLeaveRequestByRequestStatus("Pending"));
        return "viewAllPendingLeaveRequest";
    }

    @GetMapping("viewLeaveRequest")
    public String viewAllLeaveRequest (Model model) {
        model.addAttribute("listOfLeaveRequest", adminService.viewAllLeaveRequest());
        return "viewAllLeaveRequest";
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save employee to database
        adminService.saveEmployee(employee);
        return "redirect:adminDashboard";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

        // get employee from the service
        Employee employee = adminService.getEmployeeById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/approveLeaveRequest/{id}")
    public String approveRejectLeaveRequest(@PathVariable(value = "id") long id) {
        LeaveRequest leaveRequest = adminService.getLeaveRequestById(id);
        leaveRequest.setRequestStatus("Approved");
        employeeService.requestForLeave(leaveRequest);
        return "redirect:/viewPendingLeaveRequest";
    }

    @GetMapping("/rejectLeaveRequest/{id}")
    public String rejectLeaveRequest (@PathVariable (value = "id") long id) {
        LeaveRequest leaveRequest = adminService.getLeaveRequestById(id);
        leaveRequest.setRequestStatus("Rejected");
        employeeService.requestForLeave(leaveRequest);
        return "redirect:/viewPendingLeaveRequest";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {
        // call delete employee method
        this.adminService.deleteEmployeeById(id);
        return "redirect:/adminDashboard";
    }

    @GetMapping("/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo) {
        int pageSize = 5;
        ModelAndView model = new ModelAndView("adminDashboard");

        Page< Employee > page = adminService.findPaginated(pageNo, pageSize);
        List< Employee > listEmployees = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());
        model.addObject("listEmployees", listEmployees);
        System.out.println("in pagination");
        return model;
    }

    @GetMapping("/paySalary/{id}")
    public String paySalary (@PathVariable(value = "id") long id) {
        adminService.paySalaryByEmployeeId(id);
        return "redirect:/adminDashboard";
    }

//    @GetMapping("viewAttendanceHistory")
//    public String getAttendanceRange
}
