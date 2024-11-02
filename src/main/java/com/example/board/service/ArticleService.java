package com.example.board.service;

import com.example.board.dto.ArticleRequestDto;
import com.example.board.dto.ArticleResponseDto;
import com.example.board.entity.Article;
import com.example.board.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    // 전체 게시글 조회
    public List<ArticleResponseDto> getAllArticles() {
        // Iterable<Article>을 List<Article>로 변환
        List<Article> articles = (List<Article>) articleRepository.findAll();

        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }

    // 한 게시글 조회
    public ResponseEntity<ArticleResponseDto> getArticleById(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            return ResponseEntity.ok(new ArticleResponseDto(article.getTitle(), article.getContent()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 생성
    public ResponseEntity<ArticleResponseDto> createArticle(ArticleRequestDto articleRequestDto) {
        Article article = articleRequestDto.toEntity();
        articleRepository.save(article);
        return ResponseEntity.ok(new ArticleResponseDto(article.getTitle(), article.getContent()));
    }

    // 게시글 수정
    public ResponseEntity<ArticleResponseDto> updateArticle(Long id, ArticleRequestDto articleRequestDto) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.update(articleRequestDto.getTitle(), articleRequestDto.getContent());
                    articleRepository.save(article);
                    return ResponseEntity.ok(new ArticleResponseDto(article.getTitle(), article.getContent()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 게시글 삭제
    public ResponseEntity<String> deleteArticleById(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 제목으로 게시글 찾기
    public List<ArticleResponseDto> findArticlesByTitle(String title) {
        return articleRepository.findByTitleContaining(title)
                .stream()
                .map(article -> new ArticleResponseDto(article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }

    // 내용으로 게시글 찾기
    public List<ArticleResponseDto> findArticlesByContent(String content) {
        return articleRepository.findByContentContaining(content)
                .stream()
                .map(article -> new ArticleResponseDto(article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }
}
