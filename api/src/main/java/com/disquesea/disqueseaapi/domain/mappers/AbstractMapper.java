package com.disquesea.disqueseaapi.domain.mappers;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public abstract class AbstractMapper<T,D> {

    private final ModelMapper mapper;

    private final Class<T> domainType;

    private final Class<D> transferObjectType;

    public AbstractMapper(ModelMapper mapper, Class<T> domainType, Class<D> transferObjectType) {
        this.mapper = mapper;
        this.domainType = domainType;
        this.transferObjectType = transferObjectType;
    }

    public T toDomain(Object object) {
        return mapper.map(object, domainType);
    }

    public D map(Object object) {
        return mapper.map(object, transferObjectType);
    }

    public Page<D> map(Page<T> page) {
        return page.map(this::map);
    }
}
