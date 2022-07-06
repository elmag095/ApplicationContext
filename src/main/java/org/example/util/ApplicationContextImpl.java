package org.example.util;

import org.example.customException.NoSuchBeanException;
import org.example.customException.NoUniqueBeanException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext{
    Map<String, Object> context = new HashMap<>();

    public ApplicationContextImpl() {
        var reflection  = new Reflections("org.example");
        Set<Class<?>> typesAnnotatedWith = reflection.getTypesAnnotatedWith(Bean.class);
        typesAnnotatedWith.forEach(aClass -> {
            String name = aClass.getAnnotation(Bean.class).name().equals("")
                ? aClass.getSimpleName().substring(0,1).toLowerCase() + aClass.getSimpleName().substring(1)
                : aClass.getAnnotation(Bean.class).name();

            String name1 = aClass.getAnnotation(Bean.class).name();

            if (context.containsKey(name)) {
                throw new NoUniqueBeanException();
            }
            try {
                context.put(name, aClass.getConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        List<Object> collect = context.values().stream()
            .filter(o -> o.getClass().isAssignableFrom(beanType)).toList();
        if (collect.size() > 1) {
            throw new NoUniqueBeanException();
        }

        if (collect.isEmpty()) {
            throw new NoSuchBeanException();
        }

        return (T) collect.get(0);
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        Object o = context.get(name);

        if (o == null || !o.getClass().isAssignableFrom(beanType)) {
            throw new NoSuchBeanException();
        }
        return (T) o;
    }

    @Override
    public <T> Map<String, ? extends T> getAllBeans(Class<T> beanType) {
        return (Map<String, ? extends T>) context.entrySet().stream()
            .filter(e -> e.getValue().getClass().isAssignableFrom(beanType))
            .collect(Collectors.toMap(stringObjectEntry -> stringObjectEntry.getKey(), o -> o.getValue()));
    }
}
