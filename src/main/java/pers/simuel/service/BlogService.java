package pers.simuel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pers.simuel.dto.BlogQuery;
import pers.simuel.entity.Blog;

public interface BlogService {
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
