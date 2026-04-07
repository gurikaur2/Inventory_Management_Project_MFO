package com.gurnoor.inventory.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gurnoor.inventory.model.Inventory;
import com.gurnoor.inventory.service.InventoryService;

@RestController
@RequestMapping(value="/inventory_app")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	
	//Create 
	@PostMapping(value="/api/inventory/save")
	public String saveInventoryItem(@RequestBody Inventory inventory){
		return inventoryService.addItem(inventory);
	}
	
	//Read all
	@GetMapping(value="/api/inventory/all")
	public List<Inventory> getAllItems() {
	    return inventoryService.getAllItems();
	}
	
	//Read By ID
    @GetMapping(value = "/api/inventory/{id}")
    public Inventory getInventoryItembyId(@PathVariable long id) {
        return inventoryService.getItembyId(id);
    }
 
    // Update
    @PutMapping(value = "/api/inventory/update/{id}")
    public String updateInventoryItem(@PathVariable long id, @RequestBody Inventory inventory) {
        return inventoryService.updateItem(id, inventory);
    }
 
    // Delete
    @DeleteMapping(value = "/api/inventory/delete/{id}")
    public String deleteInventoryItem(@PathVariable long id) {
        return inventoryService.deleteItem(id);
    }
 
    //Filter by category
    @GetMapping(value = "/api/inventory/filter/category/{category}")
    public List<Inventory> getItemsByCategory(@PathVariable String category) {
        return inventoryService.getItemsByCategory(category);
    }
 
    //  Filter by minimum quantity
    @GetMapping(value = "/api/inventory/filter/quantity/{minQuantity}")
    public List<Inventory> getItemsByMinQuantity(@PathVariable int minQuantity) {
        return inventoryService.getItemsByMinQuantity(minQuantity);
    }
 
    // Sort by quantity or price using a query param: ?sortBy=quantity OR ?sortBy=price
    @GetMapping(value = "/api/inventory/sort")
    public List<Inventory> getSortedInventory(@RequestParam String sortBy) {
        if (sortBy.equalsIgnoreCase("price")) {
            return inventoryService.getItemsSortedByPrice();
        }
        return inventoryService.getItemsSortedByQuantity();
    }
 
    // Exception handler for generic runtime errors
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }
}
