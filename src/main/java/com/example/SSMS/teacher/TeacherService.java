package com.example.SSMS.teacher;

import com.example.SSMS.user.User;
import com.example.SSMS.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Teacher> findAll(){
        return teacherRepository.findAll();
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher saveTeacherWithAccount(Teacher teacher, String rawPassword) {
        // 1. Create user account for authentication
        User user = new User();
        user.setEmail(teacher.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(User.Role.TEACHER);
        userRepository.save(user);

        // 2. Link user to teacher and save teacher
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    public Teacher findByEmail(String email) {
        return teacherRepository.findByUserEmail(email);
    }

    @Transactional
    public void deleteById(Integer id) {
        teacherRepository.deleteById(id);
    }

    @Transactional
    public void updateTeacher(Integer id, String name, String employeeId, LocalDate dob, String phoneNumber,
                              String address, String gender, String email, List<String> subjects) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        teacher.setName(name);
        teacher.setEmployeeId(employeeId);
        teacher.setDateOfBirth(dob);
        teacher.setPhone(phoneNumber);
        teacher.setAddress(address);
        teacher.setGender(gender);
        teacher.setEmail(email);
        teacher.setSubjects(subjects);
        teacherRepository.save(teacher);
    }
}
