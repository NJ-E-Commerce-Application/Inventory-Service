package FashionFlow.inventory_service.controller;


import FashionFlow.inventory_service.service.InventoryService;
import FashionFlow.inventory_service.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    @PostMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToStock(@PathVariable String skuCode) {
        System.out.println(skuCode);
        inventoryService.addToStock(skuCode);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory(){
        log.info("Fetching all inventory records...");
        return inventoryService.getAllInventory();
    }
}