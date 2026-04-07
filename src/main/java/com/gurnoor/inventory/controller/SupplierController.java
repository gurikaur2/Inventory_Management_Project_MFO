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

import com.gurnoor.inventory.model.Supplier;
import com.gurnoor.inventory.service.SupplierService;
 

 
@RestController
@RequestMapping(value = "/inventory_app")
public class SupplierController {

	// @Autowired injection into controller (DoD a)
    @Autowired
    private SupplierService supplierService;
 
    // DoD e - API endpoints for managing suppliers
 
    @PostMapping(value = "/api/suppliers/save")
    public String saveSupplier(@RequestBody Supplier supplier) {
        return supplierService.addSupplier(supplier);
    }
 
    @GetMapping(value = "/api/suppliers/all")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
 
    @GetMapping(value = "/api/suppliers/{id}")
    public List<Supplier> getSupplierById(@PathVariable long id) {
        return supplierService.getSupplierById(id);
    }
 
    @PutMapping(value = "/api/suppliers/update/{id}")
    public String updateSupplier(@PathVariable long id, @RequestBody Supplier supplier) {
        return supplierService.updateSupplier(id, supplier);
    }
 
    @DeleteMapping(value = "/api/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable long id) {
        return supplierService.deleteSupplier(id);
    }
 
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }
}
