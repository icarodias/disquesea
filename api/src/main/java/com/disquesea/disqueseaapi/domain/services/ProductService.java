package com.disquesea.disqueseaapi.domain.services;

import com.disquesea.disqueseaapi.domain.exceptions.EntityIsBeingUsedException;
import com.disquesea.disqueseaapi.domain.exceptions.ResourceNotFoundException;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.respositories.ProductRepository;
import com.disquesea.disqueseaapi.specifications.ProductSpecification;
import com.disquesea.disqueseaapi.specifications.dto.ProductCriteriaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Page<Product> findAll(ProductCriteriaDTO criteriaDTO, Pageable pageable) {
        Specification<Product> specification = Specification
                .where(ProductSpecification.nameContains(criteriaDTO.getName()))
                .and(ProductSpecification.scaleIs(criteriaDTO.getScale()))
                .and(ProductSpecification.statusIs(criteriaDTO.getStatus()))
                .and(ProductSpecification.categoryIs(criteriaDTO.getCategory()))
                .and(ProductSpecification.catalogVisibility(criteriaDTO.getIsVisibleInCatalog()));

        return repository.findAll(specification, pageable);
    }

    public Product findById(long id) {
        final String message = String.format("Product of id %d not found", id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(message));
    }

    public Product create(Product product) {
        product.updateStatus();
        return save(product);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(long id, Product source) {
        Product target = findById(id);

        String[] ignoredProperties = {"id", "status"};
        BeanUtils.copyProperties(source, target, ignoredProperties);

        return save(target);
    }

    public void delete(long id) {
        final Product product = findById(id);

        try {
            repository.delete(product);
        } catch (DataIntegrityViolationException ex){
            final String message = String.format("Product of id %d can not be removed because it is being used.", id);
            throw new EntityIsBeingUsedException(message);
        }
    }
}
