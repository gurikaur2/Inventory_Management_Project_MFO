package com.gurnoor.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurnoor.inventory.dao.InventoryDao;
import com.gurnoor.inventory.model.Inventory;

@Service
public class InventoryService {

	
	//Field injection via @Autowired
	@Autowired
	private InventoryDao inventoryDao;
	
	public List<Inventory> getAllItems()
	{
		return inventoryDao.findAll();
	}
	
	public Inventory getItembyId(long id)
	{
		return inventoryDao.findById(id);
	}
	
	public String addItem(Inventory inventory)
	{
		inventoryDao.saveInventory(inventory);
		return "Inventory item saved successfully!";
	}
	
	public String updateItem(long id, Inventory inventory) {
        boolean updated = inventoryDao.update(id, inventory);
        return updated ? "Inventory item updated successfully!" : "Item not found with id " + id;
    }
 
    public String deleteItem(long id) {
        boolean deleted = inventoryDao.delete(id);
        return deleted ? "Inventory item deleted successfully!" : "Item not found with id " + id;
    }
 
    //  Filter by category
    public List<Inventory> getItemsByCategory(String category) {
        return inventoryDao.findByCategory(category);
    }
 
    //Filter by minimum quantity
    public List<Inventory> getItemsByMinQuantity(int minQuantity) {
        return inventoryDao.findByMinQuantity(minQuantity);
    }
 
    //Sort by quantity
    public List<Inventory> getItemsSortedByQuantity() {
        return inventoryDao.findAllSortedByQuantity();
    }
 
    // Sort by price
    public List<Inventory> getItemsSortedByPrice() {
        return inventoryDao.findAllSortedByPrice();
    }

	
}