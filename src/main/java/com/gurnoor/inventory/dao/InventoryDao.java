package com.gurnoor.inventory.dao;

import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gurnoor.inventory.model.Inventory;

@Repository
public class InventoryDao {
	
	//Constructor injection
	private final JdbcTemplate jdbcTemplate;
	
	public InventoryDao(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	//Rowmapper converts DB row to inventory object
	private final RowMapper<Inventory> rowMapper=(rs,rowNum) -> new Inventory(
			rs.getLong("inventoryId"),
			rs.getString("itemName"),
			rs.getString("category"),
			rs.getDouble("price"),
			rs.getInt("quantity"),
			rs.getLong("supplierId"),
			rs.getLong("warehouseId")
			);
	
	//create
	public void saveInventory(Inventory inventory)
	{
		String sql = "INSERT INTO INVENTORY (itemName, category, price, quantity, supplierId, warehouseId) VALUES (?, ?, ?, ?, ?, ?)";
		int rowsUpdated = jdbcTemplate.update(sql,
				//inventory.getInventoryId(),
				inventory.getItemName(),
				inventory.getCategory(),
				inventory.getPrice(),
				inventory.getQuantity(),
				inventory.getSupplierId(),
				inventory.getWarehouseId()
				);
		if (rowsUpdated == 0)
			throw new RuntimeException("Error saving inventory item!");
	}
	

	//Read all
	public List<Inventory> findAll()
	{
		String sql ="SELECT inventoryId, itemName, category, price, quantity, supplierId, warehouseId FROM INVENTORY";
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	//Read by ID
	public Inventory findById(long inventoryId)
	{
		String sql = "SELECT inventoryId, itemName, category, price, quantity, supplierId, warehouseId FROM INVENTORY WHERE inventoryId=?";
		return jdbcTemplate.queryForObject(sql, rowMapper,inventoryId);
		
	}
	
	//Update 
	public boolean update(long id, Inventory inventory)
	{
		String sql = "UPDATE INVENTORY SET itemName = ?, category = ?, price = ?, quantity = ?, supplierId = ?, warehouseId = ? WHERE inventoryId=?";
		int rows = jdbcTemplate.update(sql,
				inventory.getItemName(),
				inventory.getCategory(),
				inventory.getPrice(),
				inventory.getQuantity(),
				inventory.getSupplierId(),
				inventory.getWarehouseId(),
				id);
		return rows>0;
	}
	
	//delete
	public boolean delete(long id)
	{
		String sql = "DELETE FROM INVENTORY WHERE inventoryId=?";
		int rows = jdbcTemplate.update(sql,id);
		return rows>0;
		
	}
		
	
	//Filter by category
	public List<Inventory> findByCategory(String category)
	{
		String sql = "SELECT inventoryId, itemName, category, price, quantity, supplierId, warehouseId FROM INVENTORY WHERE category = ?";
		return jdbcTemplate.query(sql, rowMapper, category);
	}
	
	//Filter by minimum quantity
	public List<Inventory> findByMinQuantity(int minQuantity)
	{
		String sql = "SELECT inventoryId, itemName, category, price, quantity, supplierId, warehouseId FROM INVENTORY ORDER BY quantity >=?";
		return jdbcTemplate.query(sql,rowMapper);
	}
	
	//Filter by quantity Ascending
	public List<Inventory> findAllSortedByQuantity()
	{
		String sql = "SELECT inventoryId, itemName, category, price, quantity, supplierId, warehouseId FROM INVENTORY ORDER BY price ASC";
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	//Filter by price ascending
	public List<Inventory> findAllSortedByPrice()
	{
		String sql = "SELECT inventoryId, itemName, category, price, quantity, supplierId, warehouseId FROM INVENTORY ORDER BY price ASC";
		return jdbcTemplate.query(sql, rowMapper);
	}
}
