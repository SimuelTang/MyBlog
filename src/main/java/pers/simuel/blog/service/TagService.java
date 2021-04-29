package pers.simuel.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pers.simuel.blog.entity.Tag;

import java.util.List;

public interface TagService {
    Page<Tag> listTag(Pageable pageable);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Tag saveTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

    List<Tag> listTag();

    List<Tag> listTag(String tagIds);

    List<Tag> listTagTop(int i);
}
