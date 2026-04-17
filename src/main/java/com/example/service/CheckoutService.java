package com.example.service;

import com.example.dto.VariantPricingResponseDto;
import com.example.model.CartItem;
import com.example.model.OrderItem;
import com.example.model.Product;
import com.example.model.ProductImage;
import com.example.model.Variant;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductImageRepository;
import com.example.repository.ProductRepository;
import com.example.repository.VariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepo;
    private final OrderRepository orderRepo;
    private final VariantRepository variantRepo;
    private final VariantPricingService pricingService;
    private final ProductRepository productRepo;
    private final ProductImageRepository productImageRepo;
    @Transactional
    public List<OrderItem> checkout(Long userId) {
        List<CartItem> cartItems = cartRepo.findByUserId(userId);
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem c : cartItems) {

            // ⭐ Get variant to read price
            Variant variant = variantRepo.findById(c.getVariantId())
                    .orElse(null);

            double finalPrice = 0.0;

            if (variant != null) {
                VariantPricingResponseDto pricing = pricingService.getPricing(variant.getId());
                finalPrice = pricing.getFinalPrice();   // ⭐ APPLY DISCOUNT
            }

            Product product = productRepo.findById(c.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
         // 3️⃣ Resolve image (priority-based)
            String imageUrl = resolveProductImage(c.getProductId(), c.getVariantId());
            
            OrderItem o = new OrderItem();
            o.setUserId(userId);
            o.setProductId(c.getProductId());
            o.setProductName(product.getName());  
            o.setProductImage(imageUrl);
            o.setQuantity(c.getQuantity());
            o.setPrice(finalPrice);                 // ⭐ SAME PRICE AS CART
            o.setOrderStatus("CONFIRMED");

            orderItems.add(orderRepo.save(o));
        }

        cartRepo.deleteByUserId(userId);
        return orderItems;
    }



private String resolveProductImage(Long productId, Long variantId) {

    // Priority 1: Variant-specific image
    if (variantId != null) {
        List<ProductImage> variantImages =
                productImageRepo.findByProductIdAndVariantId(productId, variantId);
        if (!variantImages.isEmpty()) {
            return variantImages.get(0).getImageUrl();
        }
    }

    // Priority 2: Product image (variant null)
    List<ProductImage> productImages =
            productImageRepo.findByProductIdAndVariantIdIsNull(productId);
    if (!productImages.isEmpty()) {
        return productImages.get(0).getImageUrl();
    }

    // Priority 3: Any image
    return productImageRepo.findFirstByProductId(productId)
            .map(ProductImage::getImageUrl)
            .orElse("/images/no-image.png");
}
    
    
    public List<OrderItem> getOrders(Long userId) {
        return orderRepo.findByUserId(userId);
    }
}
