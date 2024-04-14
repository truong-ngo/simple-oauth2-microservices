package com.example.springauth.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface BaseSpecification<E> {
    default <T> Specification<E> equal(T value, String fieldName) {
        return (root, query, builder) -> builder.equal(root.get(fieldName), value);
    }

    default Specification<E> like(String pattern, String fieldName) {
        return (root, query, builder) -> builder.like(root.get(fieldName), pattern);
    }

    default Specification<E> isTrue(String fieldName) {
        return (root, query, builder) -> builder.isTrue(root.get(fieldName));
    }

    default Specification<E> isFalse(String fieldName) {
        return (root, query, builder) -> builder.isFalse(root.get(fieldName));
    }
}
