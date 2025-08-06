package com.example.SSMS.parent;

import com.example.SSMS.results.Result;
import com.example.SSMS.results.ResultService;
import com.example.SSMS.student.Student;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/parent")
public class ParentController {
    private final ParentService parentService;
    private final ResultService resultService;

    public ParentController(ParentService parentService, ResultService resultService) {
        this.parentService = parentService;
        this.resultService = resultService;
    }

    @GetMapping("/dashboard")
    public String parentDashboard(Authentication auth, Model model) {
        // Get the logged-in parent's email (or username, as appropriate)
        String parentEmail = auth.getName();
        Parent parent = parentService.findByUserEmail(parentEmail);
        // Get students registered to this parent
        List<Student> students = parent.getStudents(); // Assuming Parent has getStudents()
        model.addAttribute("parent", parent);
        model.addAttribute("students", students);
        return "parentDashboard";
    }

    @GetMapping("/results")
    public String parentResults(@RequestParam Integer studentId, Authentication auth, Model model) {
        String email = auth.getName();
        Parent parent = parentService.findByUserEmail(email);

        // Find the student (and check if they belong to this parent for security)
        Student student = parent.getStudents().stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student not found or not your child"));

        // Fetch all results for this student
        List<Result> results = resultService.findByStudentId(studentId);

        model.addAttribute("parent", parent);
        model.addAttribute("student", student);
        model.addAttribute("results", results);
        return "parentResults";
    }
}