package pers.simuel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.simuel.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
