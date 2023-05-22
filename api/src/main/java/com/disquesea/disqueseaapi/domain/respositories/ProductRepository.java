package com.disquesea.disqueseaapi.domain.respositories;

import com.disquesea.disqueseaapi.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findById(long id);
}
