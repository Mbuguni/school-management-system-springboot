package com.example.SSMS.admin;

import com.example.SSMS.news.News;
import com.example.SSMS.news.NewsRepository;
import com.example.SSMS.parent.Parent;
import com.example.SSMS.parent.ParentService;
import com.example.SSMS.student.Student;
import com.example.SSMS.student.StudentRepository;
import com.example.SSMS.student.StudentService;
import com.example.SSMS.teacher.Teacher;
import com.example.SSMS.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final StudentService studentService;
    private final ParentService parentService;
    private final TeacherService teacherService;
    private final NewsRepository newsRepository;

    @Autowired
    public AdminController(StudentService studentService, ParentService parentService, StudentRepository studentRepository, TeacherService teacherService, NewsRepository newsRepository) {
        this.studentService = studentService;
        this.parentService = parentService;
        this.teacherService = teacherService;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        // Set admin name for display
        String adminName = authentication.getName(); // or fetch from your admin User entity
        model.addAttribute("adminName", adminName);
        model.addAttribute("latestNews", newsRepository.findAll());

        // Example: add latest news if you have a news service
        // List<News> latestNews = newsService.getLatestNews();
        // model.addAttribute("latestNews", latestNews);

        return "adminDashboard"; // This will resolve to src/main/resources/templates/adminDashboard.html
    }

    @GetMapping("/students")
    public String students( Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("parents", parentService.findAll());
        return "adminStudents";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute("student") Student student,
                             @RequestParam("parentId") Long parentId) {
        studentService.saveStudent(student, parentId);
        return "redirect:/admin/students";
    }

    @PostMapping("/students/remove")
    public String deleteStudent(@RequestParam("studentId") Integer id) {
        studentService.deleteById(id);
        return "redirect:/admin/students";
    }

    @GetMapping("/teachers")
    public String teachers( Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "adminTeachers";
    }

//    @PostMapping("/teachers/add")
//    public String addTeacher(@ModelAttribute("teacher") Teacher teacher) {
//        teacherService.saveTeacher(teacher);
//        return "redirect:/admin/teachers";
//    }

    @PostMapping("/teachers/add")
    public String addTeacher(
            @RequestParam String name,
            @RequestParam String employeeId,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String gender,
            @RequestParam LocalDate dateOfBirth,
            @RequestParam List<String> subjects,
            @RequestParam String address
    ) {

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmployeeId(employeeId);
        teacher.setEmail(email);
        teacher.setPhone(phone);
        teacher.setGender(gender);
        teacher.setDateOfBirth(dateOfBirth);
        teacher.setAddress(address);
        teacher.setSubjects(subjects);

        teacherService.saveTeacherWithAccount(teacher, password);
        return "redirect:/admin/teachers";
    }

    @PostMapping("/teachers/remove")
    public String deleteTeacher(@RequestParam("teacherId") Integer id) {
        teacherService.deleteById(id);
        return "redirect:/admin/teachers";
    }

    //edit teacher
    @PostMapping("/teachers/edit/{id}")
    public String editTeacher(@PathVariable Integer id,
                              @RequestParam String name,
                              @RequestParam String employeeId,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                              @RequestParam String phoneNumber,
                              @RequestParam String address,
                              @RequestParam String gender,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) List<String> subjects) {
        teacherService.updateTeacher(id, name, employeeId, dob, phoneNumber, address, gender, email, subjects);
        return "redirect:/admin/teachers";
    }

    //parents control methods
    @GetMapping("/parents")
    public String parents( Model model) {
        model.addAttribute("parents", parentService.findAll());
        return "adminParents";
    }

    @PostMapping("/parents/add")
    public String addParent(@ModelAttribute("parent") Parent parent) {
        parentService.saveParent(parent);
        return "redirect:/admin/parents";
    }

    @PostMapping("/parents/remove")
    public String deleteParent(@RequestParam("parentId") Long id) {
        parentService.deleteById(id);
        return "redirect:/admin/parents";
    }

    //edit teacher
    @PostMapping("/parents/edit/{id}")
    public String editParent(@PathVariable Long id,
                              @RequestParam String name,
                             @RequestParam(required = false) String email,
                             @RequestParam String phone,
                             @RequestParam String address,
                             @RequestParam String gender ) {
        parentService.updateParent(id, name,email, phone,address,  gender );
        return "redirect:/admin/parents";
    }
}
