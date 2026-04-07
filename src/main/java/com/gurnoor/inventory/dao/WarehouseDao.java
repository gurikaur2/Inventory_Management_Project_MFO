package com.gurnoor.inventory.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gurnoor.inventory.model.Warehouse;


@Repository
public class WarehouseDao {
	 
	//Constructor injection
	private final JdbcTemplate jdbcTemplate;
	
	public WarehouseDao(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	
	
	//RowMapper converts Db row to warehouse object
	private final RowMapper<Warehouse> rowMapper = (rs, rowNum) -> new Warehouse(
			rs.getLong("warehouseId"),
			rs.getString("warehouseName"),
			rs.getString("warehouseLocation"),
			rs.getInt("capacity")
			);
	
	public List<Warehouse> findAll()
	{
		String sql = "SELECT warehouseid, warehouseName, warehouseLocation, capacity FROM WAREHOUSE";
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	public List<Warehouse> findById(long warehouseId)
	{
		String sql = "SELECT warehouseId, warehouseName, warehouseLocation, capacity FROM WAREHOUSE WHERE warehouseId=? ";
		return jdbcTemplate.query(sql, rowMapper,warehouseId);
	}
	
	public void saveWarehouse(Warehouse warehouse)
	{
		String sql = "INSERT INTO WAREHOUSE VALUES (?,?,?,?)";
		int rowsUpdated = jdbcTemplate.update(sql,
				warehouse.getWarehouseId(),
				warehouse.getWarehouseName(),
				warehouse.getWarehouseLocation(),
				warehouse.getCapacity());
		
		if (rowsUpdated ==0)
			throw new RuntimeException("Error saving warehouse!");
				
	}
	
	public boolean update(long id, Warehouse warehouse)
	{
		String sql= "UPDATE WAREHOUSE SET warehouseName = ? WHERE warehouseId = ?";
		 int rows = jdbcTemplate.update(sql,
                warehouse.getWarehouseName(),
                warehouse.getWarehouseLocation(),
                warehouse.getCapacity(),
                id);
        return rows > 0;
		
	}
	public boolean delete(long id) 
	{
        String sql = "DELETE FROM WAREHOUSE WHERE warehouseId = ?";
        int rows = jdbcTemplate.update(sql, id);
        return rows > 0;
    }
}
