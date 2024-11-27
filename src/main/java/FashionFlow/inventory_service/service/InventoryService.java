package FashionFlow.inventory_service.service;


import FashionFlow.inventory_service.dto.InventoryResponse;
import FashionFlow.inventory_service.model.Inventory;
import FashionFlow.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryService {

    private final InventoryRepository inventoryRepository;

    //check if the prodcut is available in the stock
    public boolean isInStock(String skuCode, Integer quantity) {
        log.info(" Start -- Received request to check stock for skuCode {}, with quantity {}", skuCode, quantity);
        boolean isInStock = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
        log.info(" End -- Product with skuCode {}, and quantity {}, is in stock - {}", skuCode, quantity, isInStock);
        return isInStock;
    }

    //create a record in the stock when a product is created
    public void addToStock(String skuCode) {
        log.info(" Start -- Received request to add stock for skuCode {}", skuCode);
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .map(existingInventory -> {
                    existingInventory.setQuantity(existingInventory.getQuantity() + 1);
                    return existingInventory;
                })
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setSkuCode(skuCode);
                    newInventory.setQuantity(1);
                    return newInventory;
                });

        inventoryRepository.save(inventory);
        log.info(" End -- Stock updated for skuCode {}. New quantity: {}", skuCode, inventory.getQuantity());
    }

    //get all inventory records
    public List<InventoryResponse> getAllInventory() {
        log.info("Fetching all inventory records...");
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> new InventoryResponse(inventory.getId(),inventory.getSkuCode(),inventory.getQuantity()))
                .toList();
    }
}