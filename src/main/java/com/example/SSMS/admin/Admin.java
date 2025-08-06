package com.example.SSMS.admin;

import com.example.SSMS.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="admin")
public class Admin {
    @Id
    @SequenceGenerator(
            name = "admin_sequence",
            sequenceName = "admin_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "admin_sequence"
    )
    private long id;
    private String name;
    private int age;
    private String address;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Admin() {
    }

    public Admin(String name, int age, String phone, String address, User user) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.user = user;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
