package com.dnlab.coffee.supply.service;

import com.dnlab.coffee.menu.repository.IngredientRepository;
import com.dnlab.coffee.menu.service.MenuService;
import com.dnlab.coffee.supply.common.SupplierDto;
import com.dnlab.coffee.supply.common.SupplyDTO;
import com.dnlab.coffee.supply.common.SupplyItemDTO;
import com.dnlab.coffee.supply.entity.Supplier;
import com.dnlab.coffee.supply.entity.Supply;
import com.dnlab.coffee.supply.entity.SupplyItem;
import com.dnlab.coffee.supply.repository.SupplierRepository;
import com.dnlab.coffee.supply.repository.SupplyItemRepository;
import com.dnlab.coffee.supply.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplierRepository supplierRepository;
    private final SupplyRepository supplyRepository;
    private final SupplyItemRepository supplyListRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuService menuService;

    public Supplier addSupplier(SupplierDto dto){
        if(supplierRepository.existsByNameAndActive(dto.getName(), true)) return null;
        return supplierRepository.save(dto.toEntity());
    }

    public void updateSupplier(long supplierId, SupplierDto dto){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if(optionalSupplier.isEmpty()) return;
        Supplier supplier = optionalSupplier.get();
        supplier.setName(dto.getName());
        supplier.setBaseAddress(dto.getBaseAddress());
        supplier.setDetailAddress(dto.getDetailAddress());
        supplier.setPostalCode(dto.getPostalCode());
        supplierRepository.save(supplier);
    }

    public void deActivateSupplier(long supplierId){
        supplierRepository.findById(supplierId).ifPresent(supplier -> supplier.setActive(false));
    }

    @Transactional
    public void confirmSupply(long supplierId, LocalDateTime suppliedDate){
        Supply supply = getSupply(supplierId);
        supply.setSuppliedDate(suppliedDate);
        supply.setConfirmed(true);

        supply.getSupplyItemList().forEach(supplyItem -> menuService.addIngredientStock(supplyItem.getIngredient(), supplyItem.getAmount()));

        supplyRepository.save(supply);
    }

    @Transactional
    public Supply getSupply(long supplierId){
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        Supply supply = supplyRepository.findBySupplierAndConfirmed(supplier, false);
        if(supply != null) return supply;
        supply = Supply.builder()
                .supplier(supplier)
                .confirmed(false)
                .build();
        return supplyRepository.save(supply);
    }
    @Transactional
    public SupplyItem addSupplyItem(long supplierId, SupplyItemDTO dto){
        Supply supply = getSupply(supplierId);
        return supplyListRepository.save(SupplyItem.builder()
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .supply(supply)
                .ingredient(ingredientRepository.findById(dto.getIngredientId()).orElseThrow())
                .build());
    }

    public Supplier getSupplier(long supplierId){
        return supplierRepository.findById(supplierId).orElse(null);
    }

    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAllByActive(true);
    }

    public List<Supply> getAllSupplies(){
        return supplyRepository.findAllByConfirmed(true);
    }
}
