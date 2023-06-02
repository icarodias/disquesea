package com.disquesea.disqueseaapi.controllers.mappers;


import org.modelmapper.ModelMapper;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;

public abstract class AbstractMapper<T,D> {

    private final ModelMapper mapper;

    public AbstractMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    public T toDomain(Object object) {
        final int DOMAIN_CLASS = 0;
        return (T) mapper.map(object, resolveType(DOMAIN_CLASS));
    }

    @SuppressWarnings("unchecked")
    public D map(Object object) {
        final int TRANSFER_OBJECT_CLASS = 1;
        return (D) mapper.map(object, resolveType(TRANSFER_OBJECT_CLASS));
    }

    public Page<D> map(Page<T> page) {
        return page.map(this::map);
    }

    private Class<?> resolveType(int type) {
        return GenericTypeResolver.resolveTypeArguments(getClass(),AbstractMapper.class)[type];
    }

}
