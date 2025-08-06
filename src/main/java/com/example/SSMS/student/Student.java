package com.example.SSMS.student;

import jakarta.persistence.*;
import com.example.SSMS.parent.Parent;

@Entity
@Table(name="student")
public class Student {

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
    private String email;
    private String admissionNumber;
    private int age;
    private String className;
    private String gender;
    private String classroom;
    private String address;
    private String phone;
    private String profileImageUrl;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    public Student() {
    }

    public Student(long id, String name, String email, String admissionNumber, int age, String className, String gender, String classroom, String address, String phone, Parent parent, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.admissionNumber = admissionNumber;
        this.age = age;
        this.className = className;
        this.gender = gender;
        this.classroom = classroom;
        this.address = address;
        this.phone = phone;
        this.parent = parent;
        this.profileImageUrl = profileImageUrl;
    }

    public Student(int age, String name, String email, String gender, String classroom, String address, String phone, Parent parent, String admissionNumber, String className, String profileImageUrl) {
        this.age = age;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.classroom = classroom;
        this.address = address;
        this.phone = phone;
        this.parent = parent;
        this.admissionNumber = admissionNumber;
        this.className = className;
        this.profileImageUrl = profileImageUrl;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

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

    public Parent getParent() { return parent; }

    public void setParent(Parent parent) { this.parent = parent; }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getClassName() {return className;}

    public void setClassName(String className) {this.className = className;}

    public String getAdmissionNumber() {return admissionNumber;}

    public void setAdmissionNumber(String admissionNumber) {this.admissionNumber = admissionNumber;}

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
