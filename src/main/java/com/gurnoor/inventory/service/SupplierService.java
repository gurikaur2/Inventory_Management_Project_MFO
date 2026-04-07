package com.gurnoor.inventory.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurnoor.inventory.dao.SupplierDao;
import com.gurnoor.inventory.model.Supplier;



@Service
public class SupplierService {

	
	// Field injection via @Autowired (DoD a)
    @Autowired
    private SupplierDao supplierDao;
 
    public List<Supplier> getAllSuppliers() {
        return supplierDao.findAll();
    }
 
    public List<Supplier> getSupplierById(long id) {
        return supplierDao.findById(id);
    }
 
    public String addSupplier(Supplier supplier) {
        supplierDao.saveSuppliers(supplier);
        return "Supplier saved successfully!";
    }
 
    public String updateSupplier(long id, Supplier supplier) {
        boolean updated = supplierDao.update(id, supplier);
        return updated ? "Supplier updated successfully!" : "Supplier not found with id " + id;
    }
 
    public String deleteSupplier(long id) {
        boolean deleted = supplierDao.delete(id);
        return deleted ? "Supplier deleted successfully!" : "Supplier not found with id " + id;
    }
}
