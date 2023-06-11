package com.disquesea.disqueseaapi.domain.services;

import com.disquesea.disqueseaapi.domain.generator.document.StorageDocumentGenerator;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ProductService productService;

    public byte[] generateStorage() {
        Map<Category,List<Product>> mapping = new HashMap<>();
        List<Category> categories = Arrays.stream(Category.values()).toList();

        categories.forEach(
                category -> mapping.put(category, productService.findAll(ProductSpecification.categoryIs(category)))
        );

        return StorageDocumentGenerator.getDocumentInByteArray(mapping);
    }
}
