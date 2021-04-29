package pers.simuel.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.simuel.blog.dao.TagRepository;
import pers.simuel.blog.entity.Tag;
import pers.simuel.blog.exceptions.NotFoundException;
import pers.simuel.blog.service.TagService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 14:30
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tagToBeChecked = tagRepository.findById(id).orElse(null);
        if (tagToBeChecked == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, tagToBeChecked);
        return tagRepository.save(tagToBeChecked);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        return tagRepository.findAllById(getTagIds(tagIds));
    }

    @Override
    public List<Tag> listTagTop(int cnt) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        PageRequest pageRule = PageRequest.of(0, cnt, sort);
        return tagRepository.findTop(pageRule);
    }

    private Iterable<Long> getTagIds(String tagIds) {
        ArrayList<Long> list = new ArrayList<>();
        if (tagIds != null && !"".equals(tagIds)) {
            String[] ids = tagIds.split(",");
            for (String id : ids) {
                list.add(Long.valueOf(id));
            }
        }
        return list;
    }


}
