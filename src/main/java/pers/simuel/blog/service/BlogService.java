package pers.simuel.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pers.simuel.blog.dto.BlogQuery;
import pers.simuel.blog.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    List<Blog> listRecommendBlogTop(int size);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(String s, Pageable pageable);

    Page<Blog> listPublishedBlog(Pageable pageable);

    Page<Blog> listBlog(Long id, Pageable pageable);

    Map<String, List<Blog>> archiveBlog();

    long countBlog();
}
