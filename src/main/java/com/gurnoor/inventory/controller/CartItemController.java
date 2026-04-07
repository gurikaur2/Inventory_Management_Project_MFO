package com.gurnoor.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gurnoor.inventory.model.CartItem;
import com.gurnoor.inventory.service.CartItemService;

/*
 * DoD 1 + DoD 2:
 *
 * POST /api/cart/add/{inventoryId}/{warehouseId}/{quantity}
 *   → 201 Created      : fresh item added to cart
 *   → 200 OK           : duplicate detected, quantity updated instead
 *   → 400 Bad Request  : quantity < 1, or exceeds available stock
 *   → 404 Not Found    : inventory item or warehouse does not exist
 *
 * DELETE /api/cart/remove/{cartItemId}
 *   → 204 No Content   : item removed successfully
 *   → 404 Not Found    : cart item ID does not exist
 */
@RestController
@RequestMapping(value = "/inventory_app")
public class CartItemController {
 
    // DoD a: @Autowired injection into controller
    @Autowired
    private CartItemService cartItemService;
 
    // ── DoD 1 + DoD 2: ADD TO CART ───────────────────────────────────
    @PostMapping(value = "/api/cart/add/{inventoryId}/{warehouseId}/{quantity}")
    public ResponseEntity<String> addToCart(
            @PathVariable long inventoryId,
            @PathVariable long warehouseId,
            @PathVariable int  quantity) {
 
        String message = cartItemService.addToCart(inventoryId, warehouseId, quantity);
 
        // DoD 1: message starts with "Item already" when a duplicate was found
        // and quantity was updated — return 200 OK (not 201, nothing was created)
        if (message.startsWith("Item already"))
            return ResponseEntity.ok(message);                           // 200 OK
 
        // Fresh insertion → 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // 201
    }
 
    // ── READ ALL cart items — 200 OK ──────────────────────────────────
    @GetMapping(value = "/api/cart/all")
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.getAllCartItems());      // 200
    }
 
    // ── READ single cart item — 200 OK | 404 via GlobalExceptionHandler
    @GetMapping(value = "/api/cart/{cartItemId}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable long cartItemId) {
        return ResponseEntity.ok(cartItemService.getCartItemById(cartItemId)); // 200
    }
 
    // ── DoD 2: DELETE from cart — 204 No Content ─────────────────────
    @DeleteMapping(value = "/api/cart/remove/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable long cartItemId) {
        cartItemService.removeFromCart(cartItemId);
        return ResponseEntity.noContent().build();                       // 204
    }
 
    // ── Extra: check if item already in cart — 200 OK ────────────────
    @GetMapping(value = "/api/cart/exists/{inventoryId}/{warehouseId}")
    public ResponseEntity<String> checkItemInCart(
            @PathVariable long inventoryId,
            @PathVariable long warehouseId) {
        boolean exists = cartItemService.isItemInCart(inventoryId, warehouseId);
        return ResponseEntity.ok(exists
            ? "Item is already in the cart."
            : "Item is not in the cart.");                               // 200
    }
 
    // ── Extra: total number of items in cart — 200 OK ────────────────
    @GetMapping(value = "/api/cart/size")
    public ResponseEntity<String> getCartSize() {
        return ResponseEntity.ok(
            "Total items in cart: " + cartItemService.getCartSize());    // 200
    }
}