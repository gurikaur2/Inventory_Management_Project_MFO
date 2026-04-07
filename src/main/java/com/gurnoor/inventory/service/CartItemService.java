package com.gurnoor.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurnoor.inventory.dao.CartItemDao;
import com.gurnoor.inventory.dao.InventoryDao;
import com.gurnoor.inventory.dao.WarehouseDao;
import com.gurnoor.inventory.exceptions.ItemNotFoundException;
import com.gurnoor.inventory.exceptions.WarehouseNotFoundException;
import com.gurnoor.inventory.model.CartItem;
import com.gurnoor.inventory.model.Inventory;

@Service
public class CartItemService {
 
    // DoD a: Field injection via @Autowired
    @Autowired
    private CartItemDao cartItemDAO;
 
    @Autowired
    private InventoryDao inventoryDAO;
 
    @Autowired
    private WarehouseDao warehouseDAO;
 
    // ── DoD 1: ADD TO CART with duplicate prevention ──────────────────
    /*
     * Full business logic flow:
     *  Step 1 — Validate inventory item exists           → 404 if not
     *  Step 2 — Validate warehouse exists                → 404 if not
     *  Step 3 — Validate requested quantity >= 1         → 400 if invalid
     *  Step 4 — Validate quantity <= available stock     → 400 if exceeds (extra rule)
     *  Step 5 — Check if same inventoryId+warehouseId already in cart
     *             YES → update quantity (DoD 1 core requirement)
     *              NO → insert new row
     *  Step 6 — If updating, ensure combined total still <= stock (extra rule)
     */
    public String addToCart(long inventoryId, long warehouseId, int quantity) {
 
        // Step 1: inventory item must exist
        Inventory item;
        try {
            item = inventoryDAO.findById(inventoryId);
        } catch (Exception e) {
            throw new ItemNotFoundException(
                "Cannot add to cart: Inventory item not found with id: " + inventoryId);
        }
 
        // Step 2: warehouse must exist
        try {
            warehouseDAO.findById(warehouseId);
        } catch (Exception e) {
            throw new WarehouseNotFoundException(
                "Cannot add to cart: Warehouse not found with id: " + warehouseId);
        }
 
        // Step 3: quantity must be at least 1
        if (quantity < 1)
            throw new IllegalArgumentException(
                "Quantity must be at least 1. Provided: " + quantity);
 
        // Step 4: quantity must not exceed available stock
        if (quantity > item.getQuantity())
            throw new IllegalArgumentException(
                "Requested quantity (" + quantity + ") exceeds available stock ("
                + item.getQuantity() + ") for item: " + item.getItemName());
 
        // Step 5: DoD 1 — check for existing cart entry
        CartItem existing = cartItemDAO.findByInventoryAndWarehouse(inventoryId, warehouseId);
 
        if (existing != null) {
            // ── DUPLICATE DETECTED ──
            // Step 6: ensure combined total does not exceed stock
            int updatedQuantity = existing.getQuantity() + quantity;
 
            if (updatedQuantity > item.getQuantity())
                throw new IllegalArgumentException(
                    "Cannot add " + quantity + " more. Cart already has "
                    + existing.getQuantity() + ". Total (" + updatedQuantity
                    + ") would exceed available stock (" + item.getQuantity() + ").");
 
            // Update existing row — no duplicate created
            cartItemDAO.updateQuantity(inventoryId, warehouseId, updatedQuantity);
 
            return "Item already in cart. Quantity updated from "
                 + existing.getQuantity() + " to " + updatedQuantity + ".";
        }
 
        // ── FRESH ENTRY ── cartItemId = 0 (AUTO_INCREMENT handles it)
        CartItem newCartItem = new CartItem(0, inventoryId, warehouseId, quantity);
        cartItemDAO.saveCartItem(newCartItem);
        return "Item added to cart successfully with quantity: " + quantity;
    }
 
    // ── READ ALL cart items ───────────────────────────────────────────
    public List<CartItem> getAllCartItems() {
        return cartItemDAO.findAll();
    }
 
    // ── READ single cart item ─────────────────────────────────────────
    public CartItem getCartItemById(long cartItemId) {
        try {
            return cartItemDAO.findById(cartItemId);
        } catch (Exception e) {
            throw new ItemNotFoundException("Cart item not found with id: " + cartItemId);
        }
    }
 
    // ── DELETE from cart ──────────────────────────────────────────────
    // DoD 2: void return — controller sends 204 No Content
    public void removeFromCart(long cartItemId) {
        boolean deleted = cartItemDAO.delete(cartItemId);
        if (!deleted)
            throw new ItemNotFoundException(
                "Cannot remove: Cart item not found with id: " + cartItemId);
    }
 
    // ── Extra business logic: check if item already in cart ──────────
    public boolean isItemInCart(long inventoryId, long warehouseId) {
        return cartItemDAO.findByInventoryAndWarehouse(inventoryId, warehouseId) != null;
    }
 
    // ── Extra business logic: total cart size ────────────────────────
    public int getCartSize() {
        return cartItemDAO.findAll().size();
    }
}
 