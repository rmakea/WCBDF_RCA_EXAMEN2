package com.upiiz.discounts.services;

import com.upiiz.discounts.entities.DiscountEntity;
import com.upiiz.discounts.repository.DiscountRepository; // Aquí faltaba el punto y coma
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository repository;

    public List<DiscountEntity> findAll() {
        return repository.findAll();
    }

    public DiscountEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Discount not found"));
    }

    public DiscountEntity save(DiscountEntity discount) {
        return repository.save(discount);
    }

    public DiscountEntity update(Long id, DiscountEntity discount){
        return repository.save(discount);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
