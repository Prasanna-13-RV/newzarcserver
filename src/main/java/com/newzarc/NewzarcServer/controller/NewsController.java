package com.newzarc.NewzarcServer.controller;

import com.newzarc.NewzarcServer.model.NewsPost;
import com.newzarc.NewzarcServer.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintException;
import java.util.List;
import java.util.Optional;

@RestController
public class NewsController {

    @Autowired
    private NewsRepo newsRepo;

    @GetMapping("/allnews")
    List<NewsPost> getAllNews() {
        return newsRepo.findAll();
    }

    @GetMapping("/news/{id}")
    Optional<NewsPost> getNewsById(@PathVariable Long id) {
        return newsRepo.findById(id);
    }

    @PostMapping("/news/create")
    NewsPost addNews(@RequestBody NewsPost news) {
        return newsRepo.save(news);
    }

    @PutMapping("/news/update/{id}")
    NewsPost updateNews(@RequestBody NewsPost addNews, @PathVariable Long id) {
        try {
            return newsRepo.findById(id)
                    .map(news -> {
                        news.setContent(addNews.getContent());
                        news.setDescription(addNews.getDescription());
                        news.setTitle(addNews.getTitle());
                        news.setImage_url(addNews.getImage_url());
                        news.setPubDate(addNews.getPubDate());

                        return newsRepo.save(news);
                    }).orElseThrow(() -> new PrintException("News Update Failed"));
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/news/delete/{id}")
    String deleteNews(@PathVariable Long id) throws PrintException {
        if (!newsRepo.existsById(id)) {
            throw new PrintException("News not found");
        };
        newsRepo.deleteById(id);
        return "News deleted successfully " + id;
    }
}
