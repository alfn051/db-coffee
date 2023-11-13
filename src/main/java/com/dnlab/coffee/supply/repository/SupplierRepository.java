package com.dnlab.coffee.supply.repository;

import com.dnlab.coffee.supply.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    public boolean existsByNameAndActive(String name, boolean active);

    public List<Supplier> findAllByActive(boolean active);
}