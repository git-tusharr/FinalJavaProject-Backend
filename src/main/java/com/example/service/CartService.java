package com.example.service;

import com.example.dto.CartItemResponse;
import com.example.model.CartItem;
import com.example.model.ProductImage;
import com.example.model.Variant;
import com.example.repository.CartRepository;
import com.example.repository.ProductImageRepository;
import com.example.repository.ProductRepository;
import com.example.repository.VariantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final ProductImageRepository imageRepo;
    private final VariantRepository variantRepo;
    private final VariantPricingService pricingService; 
    public CartItem addToCart(Long userId, Long productId, Long variantId, Integer qty) {

        // Check if same product + variant already exists
    	CartItem existing = cartRepo
                .findByUserIdAndProductIdAndVariantId(userId, productId, variantId)
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + qty);
            return cartRepo.save(existing); // Update quantity
        }

        // Else create new row
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setProductId(productId);
        item.setVariantId(variantId);
        item.setQuantity(qty);
        return cartRepo.save(item);
    }

    
    public void decreaseQty(Long id) {
        CartItem item = cartRepo.findById(id).orElseThrow();
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartRepo.save(item);
        } else {
            cartRepo.delete(item);
        }
    }

    
    
    
    public List<CartItemResponse> getCart(Long userId) {
        List<CartItem> items = cartRepo.findByUserId(userId);

        return items.stream()
                .map(item -> {
                    var productOpt = productRepo.findById(item.getProductId());
                    if (productOpt.isEmpty()) {
                        cartRepo.delete(item);
                        return null;
                    }
                    var product = productOpt.get();

                 // Try variant images first
                    List<ProductImage> imgs =
                            imageRepo.findByProductIdAndVariantId(item.getProductId(), item.getVariantId());

                    // Fallback to product-level image if no variant image exists
                    if (imgs.isEmpty()) {
                        imgs = imageRepo.findByProductIdAndVariantIdIsNull(item.getProductId());
                    }

                    // Safe extraction
                    String img = imgs.isEmpty() ? null : imgs.get(0).getImageUrl();


                    // ⭐ Variant null safety
                    Variant variant = null;
                    if (item.getVariantId() != null) {
                        variant = variantRepo.findById(item.getVariantId()).orElse(null);
                    } else {
                        cartRepo.delete(item); // remove bad item
                        return null;
                    }

                    double price = 0.0;
                    if (variant != null) {
                        var pricing = pricingService.getPricing(variant.getId());
                        price = pricing.getFinalPrice();   // ⭐ ALWAYS DISCOUNTED PRICE
                    }


                    return CartItemResponse.from(item, product.getName(), img, price);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public void remove(Long id) {
        cartRepo.deleteById(id);
    }

    @Transactional
    public void clear(Long userId) {
        cartRepo.deleteByUserId(userId);
    }
    
    public int countItems(Long userId) {
        return cartRepo.findByUserId(userId)
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    
}
