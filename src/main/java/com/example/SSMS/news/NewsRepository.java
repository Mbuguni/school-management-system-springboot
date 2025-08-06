package com.example.SSMS.news;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findTop5ByOrderByDateDesc();
}
