package com.gurnoor.inventory.model;

/*
 * CartItem represents a cart entry for an inventory item in a specific warehouse.
 * DoD 1: The UNIQUE(inventoryId, warehouseId) constraint in schema.sql + service-layer
 *         duplicate check ensures no duplicate entries — quantity is updated instead.
 */
public class CartItem {
 
    private long cartItemId;
    private long inventoryId;
    private long warehouseId;
    private int  quantity;
 
    public CartItem(long cartItemId, long inventoryId, long warehouseId, int quantity) {
        super();
        this.cartItemId  = cartItemId;
        this.inventoryId = inventoryId;
        this.warehouseId = warehouseId;
        this.quantity    = quantity;
    }
 
    public long getCartItemId()                        { return cartItemId; }
    public void setCartItemId(long cartItemId)         { this.cartItemId = cartItemId; }
 
    public long getInventoryId()                       { return inventoryId; }
    public void setInventoryId(long inventoryId)       { this.inventoryId = inventoryId; }
 
    public long getWarehouseId()                       { return warehouseId; }
    public void setWarehouseId(long warehouseId)       { this.warehouseId = warehouseId; }
 
    public int  getQuantity()                          { return quantity; }
    public void setQuantity(int quantity)              { this.quantity = quantity; }
 
    @Override
    public String toString() {
        return "CartItem [cartItemId=" + cartItemId
             + ", inventoryId=" + inventoryId
             + ", warehouseId=" + warehouseId
             + ", quantity=" + quantity + "]";
    }
}