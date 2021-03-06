package pers.simuel.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.simuel.blog.dao.TypeRepository;
import pers.simuel.blog.entity.Type;
import pers.simuel.blog.service.TypeService;
import pers.simuel.blog.exceptions.NotFoundException;

import java.util.List;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:31
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    /**
     * 内部的findAll是JPA提供的方法，查询需要分页的数据的类型
     *
     * @param pageable 如何进行分页
     * @return 分页的页数和每页的数据
     */
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    /**
     * 根据id获取分类
     *
     * @param id 页面传过来的id值，这个id值又是最初从数据库传递过去的，所以不用担心为空
     * @return
     */
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /**
     * save为JPA提供的方法
     *
     * @param type 待插入的Type
     * @return
     */
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    /**
     * 更新Type
     *
     * @param id   原有的type的id，这个不用变动，我们只更新名字即可
     * @param type 更新后的type，其名字已经被变动
     * @return
     */
    @Override
    public Type updateType(Long id, Type type) {
        Type typeToBeUpdated = typeRepository.findById(id).orElse(null);
        if (typeToBeUpdated == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, typeToBeUpdated);
        return typeRepository.save(typeToBeUpdated);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(int cnt) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        PageRequest pageRule = PageRequest.of(0, cnt, sort);
        return typeRepository.findTop(pageRule);
    }
}
