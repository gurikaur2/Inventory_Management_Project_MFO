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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
 
import com.gurnoor.inventory.model.Warehouse;
import com.gurnoor.inventory.service.WarehouseService;
 
@RestController
@RequestMapping(value="/inventory_app")
public class WarehouseController
{
	
	 // @Autowired injection into controller (DoD a)
    @Autowired
    private WarehouseService warehouseService;
 
    // DoD e - API endpoints for managing warehouses
 
    @PostMapping(value = "/api/warehouses/save")
    public String saveWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }
 
    @GetMapping(value = "/api/warehouses/all")
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }
 
    @GetMapping(value = "/api/warehouses/{id}")
    public List<Warehouse> getWarehouseById(@PathVariable long id) {
        return warehouseService.getWarehouseById(id);
    }
 
    @PutMapping(value = "/api/warehouses/update/{id}")
    public String updateWarehouse(@PathVariable long id, @RequestBody Warehouse warehouse) {
        return warehouseService.updateWarehouse(id, warehouse);
    }
 
    @DeleteMapping(value = "/api/warehouses/delete/{id}")
    public String deleteWarehouse(@PathVariable long id) {
        return warehouseService.deleteWarehouse(id);
    }
 
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }
}

