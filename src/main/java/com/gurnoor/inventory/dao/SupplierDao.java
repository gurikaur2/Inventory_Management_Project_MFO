package com.gurnoor.inventory.dao;

import java.util.List;
import com.gurnoor.inventory.model.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierDao {
	
	//Setter Injection
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	//Rowmapper converts Db row to supplier objects
	private final RowMapper<Supplier> rowMapper = (rs, rowNum) -> new Supplier(
			rs.getLong("supplierId"),
			rs.getString("supplierName"),
			rs.getString("supplierEmail"),
			rs.getString("supplierPhone"));
	
	public List<Supplier> findAll()
	{
		String sql = "SELECT supplierId, supplierName, supplierEmail, supplierPhone FROM SUPPLIER";
		return jdbcTemplate.query(sql, rowMapper);
		
	}
	
	public List<Supplier> findById(long supplierId)
	{
		String sql = "SELECT supplierId, supplierName, supplierEmail, supplierPhone FROM SUPPLIER WHERE supplierId =?";
		return jdbcTemplate.query(sql, rowMapper, supplierId);
	}
	
	public List<Supplier> saveSuppliers(Supplier supplier)
	{
		 String sql = "INSERT INTO SUPPLIER VALUES (?, ?, ?, ?)";
	        int rowsUpdated = jdbcTemplate.update(sql,
	                supplier.getSupplierId(),
	                supplier.getSupplierName(),
	                supplier.getSupplierEmail(),
	                supplier.getSupplierPhone());
	        if (rowsUpdated == 0)
	            throw new RuntimeException("Error saving supplier!");
			return null;
	}
	
	public boolean update(long id, Supplier supplier) {
        String sql = "UPDATE SUPPLIER SET supplierName = ?, supplierEmail = ?, supplierPhone = ? WHERE supplierId = ?";
        int rows = jdbcTemplate.update(sql,
                supplier.getSupplierName(),
                supplier.getSupplierEmail(),
                supplier.getSupplierPhone(),
                id);
        return rows > 0;
    }
	
	
	public boolean delete(long id) {
        String sql = "DELETE FROM SUPPLIER WHERE supplierId = ?";
        int rows = jdbcTemplate.update(sql, id);
        return rows > 0;
    }
			
}

