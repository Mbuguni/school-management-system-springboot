package com.example.SSMS.results;

import com.example.SSMS.parent.Parent;
import com.example.SSMS.parent.ParentService;
import com.example.SSMS.student.Student;
import com.example.SSMS.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    private final StudentRepository studentRepository;
    private final ParentService parentService;
    @Autowired
    public ResultService(ResultRepository resultRepository, StudentRepository studentRepository, ParentService parentService) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.parentService = parentService;
    }


    public void addResult(Integer studentId, String subject, Double score, String remarks) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Result result = new Result();
        result.setStudent(student);
        result.setSubject(subject);
        result.setScore(score);
        result.setDate(LocalDate.now());
        result.setGrade(calculateGrade(score));
        result.setRemarks(remarks);
        resultRepository.save(result);
    }

    public char calculateGrade(double score) {
        if (score >= 80 && score <= 100) {
            return 'A';
        } else if (score >= 61 && score <= 79) {
            return 'B';
        } else if (score >= 51 && score <= 60) {
            return 'C';
        } else if (score >= 31 && score <= 50) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public List<Result> findByStudentId(Integer studentId) {
        return resultRepository.findByStudentId(studentId);
    }

    public void sendResultToParent(Integer studentId, Integer resultId) {
        Result result = resultRepository.findById(resultId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();
        Parent parent = student.getParent();
        String message = "Result for " + result.getSubject() + ": " + result.getScore() + ", Grade: " + result.getGrade() + ", Remarks: " + result.getRemarks();
        parentService.sendInfoToParent(studentId, message);
    }
}