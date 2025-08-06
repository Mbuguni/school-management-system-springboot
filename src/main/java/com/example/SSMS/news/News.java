package com.example.SSMS.news;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String summary;

    private String imageUrl;

    private LocalDate date;

    // Constructors, getters, setters

    public News() {}

    public News(String title, String summary, String imageUrl, LocalDate date) {
        this.title = title;
        this.summary = summary;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    // getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}