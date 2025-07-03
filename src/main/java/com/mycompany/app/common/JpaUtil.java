package com.mycompany.app.common;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

/**
 * JPA避免一些空值对于动态部分更新的影响工具类
 * (null值不更新, 配合实体类@DynamicUpdate使用)
 */
public class  JpaUtil {

    /**
     * 查询出entity值 -> JpaUtil.copyNotNullProperties(input, entity); -> save(entity)
     *
     * @param inputEntity  输入实体类
     * @param dbEntity 数据库查询出的实体类
     */
    public static void copyNotNullProperties(Object inputEntity, Object dbEntity) {
        BeanUtils.copyProperties(inputEntity, dbEntity, getNullPropertyNames(inputEntity));
    }

    /**
     * 忽略的字段：null对象、整数0、浮点数0.0、空字符串""
     * <p>
     * ignoreProperties {@link BeanUtils#copyProperties(java.lang.Object, java.lang.Object, java.lang.String...)}
     *
     * @param object 传入全部字段
     * @return 忽略的字段
     */
    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors()).map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null
                        || "0.0".equals(String.valueOf(wrapper.getPropertyValue(propertyName)))
                        || "0".equals(String.valueOf(wrapper.getPropertyValue(propertyName)))
                        || "".equals(String.valueOf(wrapper.getPropertyValue(propertyName))))
                .toArray(String[]::new);
    }

}