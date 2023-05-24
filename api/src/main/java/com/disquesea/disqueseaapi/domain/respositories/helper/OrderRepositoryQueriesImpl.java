package com.disquesea.disqueseaapi.domain.respositories.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class OrderRepositoryQueriesImpl implements OrderRepositoryQueries {

    @PersistenceContext
    private final EntityManager manager;

    public OrderRepositoryQueriesImpl( EntityManager manager){
        this.manager = manager;
    }

    @Override
    @Transactional
    public void deleteAllOrders() {
        manager.createNativeQuery("delete from tb_orders").executeUpdate();
    }
}
