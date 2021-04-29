package pers.simuel.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.simuel.blog.dao.BlogRepository;
import pers.simuel.blog.dto.BlogQuery;
import pers.simuel.blog.entity.Blog;
import pers.simuel.blog.entity.Type;
import pers.simuel.blog.exceptions.NotFoundException;
import pers.simuel.blog.service.BlogService;
import pers.simuel.blog.utils.MarkdownUtils;
import pers.simuel.blog.utils.MyBeanUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author simuel_tang
 * @Date 2021/4/27
 * @Time 10:47
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    /**
     * 管理员页面，根据分页的条件，动态查询出博客
     *
     * @param pageable
     * @param blogQuery
     * @return
     */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {

        return blogRepository.findAll((Specification<Blog>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + blogQuery.getTitle() + "%"));
            }
            if (blogQuery.getTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
            }
            if (blogQuery.isRecommend()) {
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    /**
     * 管理员界面，通过id查询博客
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blogToBeUpdated = blogRepository.findById(id).orElse(null);
        if (blogToBeUpdated == null) {
            throw new NotFoundException("这篇博客不存在");
        }
        BeanUtils.copyProperties(blog, blogToBeUpdated, MyBeanUtils.getNullPropertyNames(blog));
        blogToBeUpdated.setUpdateTime(new Date());
        return blogRepository.save(blogToBeUpdated);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * 游客界面，通过点击某篇博客显示其内容
     *
     * @param id
     * @return
     */
    @Override
    public Blog getAndConvert(Long id) {
        Blog blogToBeFound = blogRepository.findById(id).orElse(null);
        if (blogToBeFound == null) {
            throw new NotFoundException("当前博客不存在");
        }
        Blog blogToBeDisplayed = new Blog();
        BeanUtils.copyProperties(blogToBeFound, blogToBeDisplayed);
        String content = blogToBeDisplayed.getContent();
        blogToBeDisplayed.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return blogToBeDisplayed;
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }
}