package com.example.SSMS.teacher;

import com.example.SSMS.student.Student;
import com.example.SSMS.results.Result;
import com.example.SSMS.student.StudentRepository;
import com.example.SSMS.teacher.TeacherService;
import com.example.SSMS.student.StudentService;
import com.example.SSMS.results.ResultService;
import com.example.SSMS.parent.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ResultService resultService;
    private final ParentService parentService;
    private final StudentRepository studentRepository;

    @Autowired
    public TeacherController(TeacherService teacherService, StudentService studentService, ResultService resultService, ParentService parentService, StudentRepository studentRepository) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.resultService = resultService;
        this.parentService = parentService;
        this.studentRepository = studentRepository;
    }

    // Main dashboard
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String teacherName = auth.getName();
        model.addAttribute("teacherName", teacherName);
        return "teacherDashboard";
    }

    // List students assigned to this teacher
//    @GetMapping("/students")
//    public String viewStudents(Authentication auth, Model model) {
//        // Optionally filter students by teacher
//        String teacherEmail = auth.getName();
//        List<Student> students = studentService.findStudentsByTeacherEmail(teacherEmail);
//        model.addAttribute("students", students);
//        return "teacherStudents";
//    }
//    @GetMapping("/students")
//    public String viewStudents(Authentication auth, Model model) {
//        // Optionally filter students by teacher
//        String teacherEmail = auth.getName();
//        List<Student> students = studentService.findAll();
//        model.addAttribute("students", students);
//        return "teacherStudents";
//    }

    @GetMapping("/students")
    public String viewStudents(Authentication auth, Model model) {
        String teacherEmail = auth.getName();
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);

        // Fetch this teacher's subjects
        Teacher teacher = teacherService.findByEmail(teacherEmail);
        List<String> subjects = teacher.getSubjects(); // If getSubjects returns Subject objects, map to names

        model.addAttribute("subjects", subjects);

        return "teacherStudents";
    }

    // Add a result for a student
    @PostMapping("/results/add")
    public String addResult(@RequestParam Integer studentId,
                            @RequestParam String subject,
                            @RequestParam Double score,
                            @RequestParam String remarks) {
        resultService.addResult(studentId, subject, score, remarks);
        return "redirect:/teacher/students";
    }

    // View all results for a student
    @GetMapping("/students/{studentId}/results")
    public String viewStudentResults(@PathVariable Integer studentId, Model model) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        List<Result> results = resultService.findByStudentId(studentId);
        model.addAttribute("results", results);
        model.addAttribute("student", student); // pass student object!
        return "teacherResults";
    }

//     Send messageinfo to parent email
    @PostMapping("/send-info")
    public String sendInfoToParent(@RequestParam Integer studentId,
                                   @RequestParam String message) {
        parentService.sendInfoToParent(studentId, message);
        return "redirect:/teacher/students";
    }

    @PostMapping("/results/send")
    public String sendResultToParent(
            @RequestParam Integer studentId,
            @RequestParam Integer resultId,
            RedirectAttributes redirectAttributes
    ) {
        // resultService.sendResultToParent(studentId, resultId);
        redirectAttributes.addFlashAttribute("message", "Result sent to parent!");
        return "redirect:/teacher/students/" + studentId + "/results";
    }
}