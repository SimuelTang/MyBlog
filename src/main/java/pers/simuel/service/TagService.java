package pers.simuel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pers.simuel.entity.Tag;

public interface TagService {
    Page<Tag> listTag(Pageable pageable);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Tag saveTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
