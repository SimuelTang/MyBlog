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
import pers.simuel.blog.dao.CommentRepository;
import pers.simuel.blog.dto.BlogQuery;
import pers.simuel.blog.entity.Blog;
import pers.simuel.blog.entity.Comment;
import pers.simuel.blog.entity.Type;
import pers.simuel.blog.exceptions.NotFoundException;
import pers.simuel.blog.service.BlogService;
import pers.simuel.blog.utils.MarkdownUtils;
import pers.simuel.blog.utils.MyBeanUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @Author simuel_tang
 * @Date 2021/4/27
 * @Time 10:47
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

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
            if (blogQuery.getTitle() != null && !"".equals(blogQuery.getTitle())) {
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
        // 更新时忽略所有的空值
        BeanUtils.copyProperties(blog, blogToBeUpdated, MyBeanUtils.getNullPropertyNames(blog));
        blogToBeUpdated.setUpdateTime(new Date());
        return blogRepository.save(blogToBeUpdated);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        // 获取待删除的博客并将其外键置空
        Blog blogToBeDeleted = getBlog(id);
        if (blogToBeDeleted == null) {
            throw new NotFoundException("没有找到这篇博客");
        }
        blogToBeDeleted.setUser(null);
        blogToBeDeleted.setType(null);
        blogRepository.save(blogToBeDeleted);
        // 获取这篇博客下的所有评论并将外键置空
        List<Comment> comments = commentRepository.findByBlogId(blogToBeDeleted.getId());
        for (Comment comment : comments) {
            comment.setBlog(null);
            comment.setParentComment(null);
        }
        commentRepository.saveAll(comments);
        // 删除博客和该博客下的评论
        blogRepository.deleteById(id);
        commentRepository.deleteAll(comments);
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

    @Override
    public Page<Blog> listPublishedBlog(Pageable pageable) {
        return blogRepository.findPublishedBlog(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll((Specification<Blog>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<String, Long> join = root.join("tags");
            return criteriaBuilder.equal(join.get("id"), tagId);
        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        // 获取所有年份
        List<String> years = blogRepository.findGroupYear();
        // 根据年份查找对应的博客并添加至Map中
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public long countBlog() {
        return blogRepository.count();
    }
}
