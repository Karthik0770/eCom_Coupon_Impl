package com.monk.backend.repository;

import com.monk.backend.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Integer> {
}
