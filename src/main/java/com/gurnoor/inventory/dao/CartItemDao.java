package com.gurnoor.inventory.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gurnoor.inventory.model.CartItem;

@Repository
public class CartItemDao {
 
    // Constructor injection (DoD b)
    private final JdbcTemplate jdbcTemplate;
 
    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 
    // RowMapper converts a DB row → CartItem object
    private final RowMapper<CartItem> rowMapper = (rs, rowNum) -> new CartItem(
            rs.getLong("cartItemId"),
            rs.getLong("inventoryId"),
            rs.getLong("warehouseId"),
            rs.getInt("quantity")
    );
 
    // READ ALL cart items
    public List<CartItem> findAll() {
        String sql = "SELECT cartItemId, inventoryId, warehouseId, quantity FROM CART_ITEM";
        return jdbcTemplate.query(sql, rowMapper);
    }
 
    // READ single cart item by primary key
    public CartItem findById(long cartItemId) {
        String sql = "SELECT cartItemId, inventoryId, warehouseId, quantity "
                   + "FROM CART_ITEM WHERE cartItemId = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, cartItemId);
    }
 
    /*
     * DoD 1 — Duplicate check:
     * Looks for an existing cart row with the SAME inventoryId + warehouseId.
     * Returns the existing CartItem if found, or null if this is a fresh entry.
     * This allows the service to decide: update quantity OR insert a new row.
     */
    public CartItem findByInventoryAndWarehouse(long inventoryId, long warehouseId) {
        String sql = "SELECT cartItemId, inventoryId, warehouseId, quantity "
                   + "FROM CART_ITEM WHERE inventoryId = ? AND warehouseId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, inventoryId, warehouseId);
        } catch (EmptyResultDataAccessException e) {
            return null; // No existing entry — safe to insert fresh
        }
    }
 
    // INSERT a brand new cart row (only called when no duplicate exists)
    public void saveCartItem(CartItem cartItem) {
        String sql = "INSERT INTO CART_ITEM (inventoryId, warehouseId, quantity) "
                   + "VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                cartItem.getInventoryId(),
                cartItem.getWarehouseId(),
                cartItem.getQuantity());
        if (rows == 0)
            throw new RuntimeException("Error saving cart item!");
    }
 
    /*
     * DoD 1 — Quantity update:
     * Called INSTEAD of saveCartItem() when a duplicate (inventoryId + warehouseId)
     * is detected. Updates the quantity of the existing row.
     */
    public void updateQuantity(long inventoryId, long warehouseId, int newQuantity) {
        String sql = "UPDATE CART_ITEM SET quantity = ? "
                   + "WHERE inventoryId = ? AND warehouseId = ?";
        jdbcTemplate.update(sql, newQuantity, inventoryId, warehouseId);
    }
 
    // DELETE a cart item by primary key
    public boolean delete(long cartItemId) {
        String sql = "DELETE FROM CART_ITEM WHERE cartItemId = ?";
        return jdbcTemplate.update(sql, cartItemId) > 0;
    }
}