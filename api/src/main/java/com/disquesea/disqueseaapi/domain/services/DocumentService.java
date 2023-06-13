package com.disquesea.disqueseaapi.domain.services;

import com.disquesea.disqueseaapi.domain.generator.document.OrderHistoryDocumentGenerator;
import com.disquesea.disqueseaapi.domain.generator.document.StorageDocumentGenerator;
import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.model.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.disquesea.disqueseaapi.specifications.ProductSpecification.catalogVisibility;
import static com.disquesea.disqueseaapi.specifications.ProductSpecification.categoryIs;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ProductService productService;

    private final OrderService orderService;

    public byte[] generateStorage() {
        Map<Category,List<Product>> mapping = new HashMap<>();
        List<Category> categories = Arrays.stream(Category.values()).toList();

        categories.forEach(
                category -> mapping.put(category, productService.findAll(categoryIs(category)))
        );

        return StorageDocumentGenerator.getDocumentInByteArray(mapping);
    }

    public byte[] generateCatalog() {
        Map<Category,List<Product>> mapping = new HashMap<>();
        List<Category> categories = Arrays.stream(Category.values()).toList();

        categories.forEach(
                category -> {
                    Specification<Product> specification = filterByCategoryAndCatalogVisible(category);
                    mapping.put(category, productService.findAll(specification));
                }
        );

        return StorageDocumentGenerator.getDocumentInByteArray(mapping);
    }

    public byte[] generateOrderHistory() {
        List<Order> orders = orderService.findAll();
        return OrderHistoryDocumentGenerator.getDocumentInByteArray(orders);
    }

    private Specification<Product> filterByCategoryAndCatalogVisible(Category category){
      return Specification.where(categoryIs(category)).and(catalogVisibility(true));
    }
}
