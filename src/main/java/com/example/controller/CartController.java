package com.example.controller;

import com.example.dto.AddToCartDto;
import com.example.dto.CartItemResponse;
import com.example.model.CartItem;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartItem add(@RequestBody AddToCartDto dto) {
        return cartService.addToCart(dto.getUserId(), dto.getProductId(), dto.getVariantId(), dto.getQuantity());
    }

    @PutMapping("/decrease/{id}")
    public void decrease(@PathVariable Long id) {
        cartService.decreaseQty(id);
    }

    
    
    @GetMapping("/{userId}")
    public List<CartItemResponse> getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/remove/{id}")
    public void removeItem(@PathVariable Long id) {
        cartService.remove(id);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clear(userId);
    }
    @GetMapping("/count")
    public int getCount(@RequestParam Long userId) {
        return cartService.countItems(userId);
    }

}
