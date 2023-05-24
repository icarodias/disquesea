package com.disquesea.disqueseaapi.domain.respositories;

import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.respositories.helper.OrderRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>, OrderRepositoryQueries {

    void deleteAll();
}
