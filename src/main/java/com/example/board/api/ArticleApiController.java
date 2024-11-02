package com.example.board.api;

import com.example.board.dto.ArticleResponseDto;
import com.example.board.service.ArticleService;
import com.example.board.dto.ArticleRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/articles")
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    // GET: 전체 게시글 조회
    @GetMapping
    public List<ArticleResponseDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    // GET: 한 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    // POST: 게시글 생성
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        return articleService.createArticle(articleRequestDto);
    }

    // PATCH: 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id,
                                                            @RequestBody ArticleRequestDto articleRequestDto) {
        return articleService.updateArticle(id, articleRequestDto);
    }

    // DELETE: 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

    // GET: 추가 구현 사항: 제목으로 게시글 찾기
    @GetMapping("/search/title/{title}")
    public List<ArticleResponseDto> findArticlesByTitle(@PathVariable String title) {
        return articleService.findArticlesByTitle(title);
    }

    // 추가 구현 사항: 내용으로 게시글 찾기
    @GetMapping("/search/content/{content}")
    public List<ArticleResponseDto> findArticlesByContent(@PathVariable String content) {
        return articleService.findArticlesByContent(content);
    }
}
