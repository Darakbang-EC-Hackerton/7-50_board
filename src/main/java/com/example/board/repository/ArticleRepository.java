package com.example.board.repository;

import com.example.board.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    // 제목에 특정 문자열이 포함된 게시글을 찾는 메서드
    List<Article> findByTitleContaining(String title);

    // 내용에 특정 문자열이 포함된 게시글을 찾는 메서드
    List<Article> findByContentContaining(String content);
}
