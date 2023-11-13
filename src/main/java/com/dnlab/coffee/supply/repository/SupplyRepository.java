package com.dnlab.coffee.supply.repository;

import com.dnlab.coffee.supply.entity.Supplier;
import com.dnlab.coffee.supply.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    public Supply findBySupplierAndConfirmed(Supplier supplier, boolean confirmed);

    public List<Supply> findAllByConfirmed(boolean confirmed);
}