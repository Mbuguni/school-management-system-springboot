package com.example.SSMS.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Controller
@RequestMapping("/admin/news")
public class NewsController {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping
    public String listNews(Model model) {
        List<News> newsList = newsRepository.findAll();
        model.addAttribute("newsList", newsList);
        return "newsListing";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("news", new News());
        return "newsForm";
    }

    @PostMapping("/add")
    public String addNews(@ModelAttribute News news,
                          @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String imageUrl = handleImageUpload(imageFile);
        if (imageUrl != null) {
            news.setImageUrl(imageUrl);
        }
        if (news.getDate() == null) {
            news.setDate(LocalDate.now());
        }
        newsRepository.save(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        News news = newsRepository.findById(id).orElseThrow();
        model.addAttribute("news", news);
        return "newsForm";
    }

    @PostMapping("/edit/{id}")
    public String editNews(@PathVariable Integer id, @ModelAttribute News news,
                           @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        News existingNews = newsRepository.findById(id).orElseThrow();
        String imageUrl = handleImageUpload(imageFile);
        if (imageUrl != null) {
            news.setImageUrl(imageUrl);
        } else {
            news.setImageUrl(existingNews.getImageUrl());
        }
        news.setId(id.longValue());
        newsRepository.save(news);
        return "redirect:/admin/news";
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable Integer id) {
        newsRepository.deleteById(id);
        return "redirect:/admin/news";
    }

    @PostMapping("/deleteAll")
    public String deleteAllNews() {
        newsRepository.deleteAll();
        return "redirect:/admin/news";
    }

    // Change the path to an external directory for writing uploads at runtime.
    private String handleImageUpload(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) return null;
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        // Store outside of src/main/resources to allow writing at runtime
        Path uploadPath = Paths.get("uploads/news/");
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        imageFile.transferTo(filePath);
        return "/uploads/news/" + fileName;
    }
}