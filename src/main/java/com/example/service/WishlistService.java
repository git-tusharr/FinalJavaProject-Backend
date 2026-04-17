package com.example.service;

import com.example.dto.WishlistResponse;
import com.example.model.Product;
import com.example.model.ProductImage;
import com.example.model.WishlistItem;
import com.example.repository.ProductImageRepository;
import com.example.repository.ProductRepository;
import com.example.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepo;
    private final ProductRepository productRepo;
    private final ProductImageRepository imageRepo;

    public WishlistItem add(WishlistItem item) {
        return wishlistRepo.save(item);
    }

    public List<WishlistResponse> get(Long userId) {
        return wishlistRepo.findByUserId(userId)
                .stream()
                .map(w -> {
                    Product p = productRepo.findById(w.getProductId())
                            .orElse(null);

                    // fetch first image for that product
                    ProductImage img = imageRepo.findFirstByProductId(w.getProductId()).orElse(null);
                    String url = img != null ? img.getImageUrl() : null;

                    return new WishlistResponse(
                            w.getId(),
                            w.getProductId(),
                            p != null ? p.getName() : null,
                            url
                    );
                })
                .toList();
    }

    @Transactional
    public void remove(Long userId, Long productId) {
        wishlistRepo.deleteByUserIdAndProductId(userId, productId);
    }
}
