package pers.simuel.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.simuel.dao.BlogRepository;
import pers.simuel.dto.BlogQuery;
import pers.simuel.entity.Blog;
import pers.simuel.entity.Type;
import pers.simuel.exceptions.NotFoundException;
import pers.simuel.service.BlogService;

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
     * 根据分页的条件，动态查询出博客
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
        BeanUtils.copyProperties(blog, blogToBeUpdated);
        blogToBeUpdated.setUpdateTime(new Date());
        return blogRepository.save(blogToBeUpdated);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
