package com.dnlab.coffee.supply.repository;

import com.dnlab.coffee.supply.entity.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
}