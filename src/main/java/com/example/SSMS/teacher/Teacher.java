package com.example.SSMS.teacher;

import com.example.SSMS.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="teacher")
public class Teacher {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private long id;
    private String name;
    private int age;
    private LocalDate dateOfBirth;
    private String gender;
    private String classroom;
    private String address;
    private String phone;
    private String email;
    private String employeeId;
    @ElementCollection
    private List<String> subjects;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Teacher() {
    }

    public Teacher(long id, String name, int age, String classroom, String gender, String address, String phone,  User user, String email, String employeeId, List<String> subjects) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.classroom = classroom;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.user = user;
        this.email = email;
        this.employeeId = employeeId;
        this.subjects = subjects;
    }

    public Teacher(int age, String name, String gender, String classroom, String address, String phone, User user, String email, String employeeId, List<String> subjects) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.classroom = classroom;
        this.address = address;
        this.phone = phone;
        this.user = user;
        this.email = email;
        this.employeeId = employeeId;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {this.user = user;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}
}