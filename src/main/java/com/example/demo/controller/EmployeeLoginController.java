package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.models.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.service.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EmployeeLoginController {
    @Autowired
    public EmployeeLoginService employeeLoginService;

    @Autowired
    public EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String showForm(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeLoginForm", employeeDto);
        return "index";
    }

    @PostMapping("/")
    public String processForm(@Valid EmployeeDto employeeDto, BindingResult result,
                              Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "index";
        }
//        model.addAttribute("")
        Optional<Employee> employee = employeeRepository.findEmployeeByUsernameAndPassword(employeeDto.getUsername(), employeeDto.getPassword());
        if(employee.isPresent()){
            Employee employee1 = employee.get();
            HttpSession session = request.getSession();
            session.setAttribute("employee", employee1);
            if (employee1.getUserRole().equalsIgnoreCase("admin")) {
                System.out.println("found");
//                model.addAttribute("login", true);
                return "redirect:adminDashboard";
            } else {
                return "redirect:employeeDashboard";
            }
        }else{
            System.out.println("invalid login");
            model.addAttribute("login", "invalid username/password");
//            result.rejectValue("username","invalid user");
            return "redirect:/";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
