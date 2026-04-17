package com.example.controller;

import com.example.dto.WishlistResponse;
import com.example.model.WishlistItem;
import com.example.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add")
    public WishlistItem addToWishlist(@RequestBody WishlistItem item) {
        return wishlistService.add(item);
    }

    @GetMapping("/{userId}")
    public List<WishlistResponse> getWishlist(@PathVariable Long userId) {
        return wishlistService.get(userId);
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam Long userId, @RequestParam Long productId) {
        wishlistService.remove(userId, productId);
    }
}
