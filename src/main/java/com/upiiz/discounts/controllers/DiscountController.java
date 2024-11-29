package com.upiiz.discounts.controllers;

import com.upiiz.discounts.entities.DiscountEntity;
import com.upiiz.discounts.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/discounts")
public class DiscountController {

    @Autowired
    private DiscountService service;

    @GetMapping("/listas")
    public List<DiscountEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/lista/{id}")
    public DiscountEntity getById(@PathVariable Long id) { // Cambiado a Integer
        return service.findById(id);
    }

    @PostMapping("/crear")
    public DiscountEntity create(@RequestBody DiscountEntity discount) {
        return service.save(discount);
    }

    @PutMapping("/actualizar/{id}")
    public DiscountEntity update(@PathVariable Long id, @RequestBody DiscountEntity discount) { // Cambiado a Integer
        return service.update(id, discount);
    }

    @DeleteMapping("/eliminar/{id}")
    public void delete(@PathVariable Long id) { // Cambiado a Integer
        service.delete(id);
    }
}
