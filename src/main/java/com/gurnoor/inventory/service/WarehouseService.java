package com.gurnoor.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurnoor.inventory.dao.WarehouseDao;
import com.gurnoor.inventory.model.Warehouse;

@Service
public class WarehouseService {
	
	// Field injection via @Autowired (DoD a)
    @Autowired
    private WarehouseDao warehouseDao;
 
    public List<Warehouse> getAllWarehouses() {
        return warehouseDao.findAll();
    }
 
    public List<Warehouse> getWarehouseById(long id) {
        return warehouseDao.findById(id);
    }
 
    public String addWarehouse(Warehouse warehouse) {
        warehouseDao.saveWarehouse(warehouse);
        return "Warehouse saved successfully!";
    }
 
    public String updateWarehouse(long id, Warehouse warehouse) {
        boolean updated = warehouseDao.update(id, warehouse);
        return updated ? "Warehouse updated successfully!" : "Warehouse not found with id " + id;
    }
 
    public String deleteWarehouse(long id) {
        boolean deleted = warehouseDao.delete(id);
        return deleted ? "Warehouse deleted successfully!" : "Warehouse not found with id " + id;
    }
}
