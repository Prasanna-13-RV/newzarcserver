package com.newzarc.NewzarcServer.repository;

import com.newzarc.NewzarcServer.model.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepo extends JpaRepository<NewsPost, Long> {
}
