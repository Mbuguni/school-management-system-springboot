package com.example.SSMS.parent;

import com.example.SSMS.student.Student;
import com.example.SSMS.student.StudentRepository;
import com.example.SSMS.teacher.Teacher;
import com.example.SSMS.user.User;
import com.example.SSMS.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParentService {
    private final ParentRepository parentRepository;
    private final UserService userService;
    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public ParentService(ParentRepository parentRepository, UserService userService, StudentRepository studentRepository, JavaMailSender mailSender) {
        this.parentRepository = parentRepository;
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.mailSender = mailSender;
    }

    public Parent registerParent(String name, String phone, String email, String rawPassword, String address, String gender) {
        System.out.println("Registering parent: " + name + ", " + phone + ", " + gender + ", " + address + ", " + email);
        User user = userService.registerParentUser(email, rawPassword);
        Parent parent = new Parent(name, phone, email, gender, address, user); // <-- correct order!
        return parentRepository.save(parent);
    }

    public Parent findByUserEmail(String email) {
        return parentRepository.findByUserEmail(email);
    }

    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    public Parent saveParent(Parent parent) {
        return parentRepository.save(parent);
    }

    @Transactional
    public void deleteById(Long id) {
        parentRepository.deleteById(id);
    }

    @Transactional
    public void updateParent(Long id, String name, String email, String phone, String address,
                              String gender) {
        Parent parent = parentRepository.findById(id).orElseThrow();
        parent.setName(name);
        parent.setEmail(email);
        parent.setPhone(phone);
        parent.setAddress(address);
        parent.setGender(gender);
    }

    public void sendInfoToParent(Integer studentId, String message) {
        // Find the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // Get the parent (assume getParent() returns Parent)
        Parent parent = student.getParent();
        if (parent == null) {
            throw new IllegalStateException("No parent found for student");
        }

        String parentEmail = parent.getEmail();

        // Send email (or SMS or whatever method you use)
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(parentEmail);
        mailMessage.setSubject("Information from Haven Secondary School");
        mailMessage.setText(message);

        mailSender.send(mailMessage);

        // Alternatively, log or handle notification as needed
    }
}