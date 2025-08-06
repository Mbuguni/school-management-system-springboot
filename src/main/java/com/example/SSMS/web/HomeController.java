package com.example.SSMS.web;

import com.example.SSMS.news.News;
import com.example.SSMS.news.NewsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {
    private final NewsRepository newsRepository;
    public HomeController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    @GetMapping({"/", "/index"})
    public String showIndex(Model model) {
        List<News> latestNews = newsRepository.findTop5ByOrderByDateDesc(); // Example method
        model.addAttribute("latestNews", latestNews);
        return "index";
    }
}