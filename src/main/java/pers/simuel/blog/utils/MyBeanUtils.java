package pers.simuel.blog.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import pers.simuel.blog.entity.Blog;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author simuel_tang
 * @Date 2021/4/29
 * @Time 8:35
 */
public class MyBeanUtils {
    public static String[] getNullPropertyNames(Blog blog) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(blog);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[0]);
    }
}
