package com.example.dto;

import com.example.model.CartItem;
import lombok.Data;

@Data
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private Long productId;
    private String productName;
    private String image;
    private Double price;
    private Long variantId;

    public static CartItemResponse from(
            CartItem item,
            String productName,
            String image,
            Double price
    ) {
        CartItemResponse dto = new CartItemResponse();
        dto.id = item.getId();
        dto.quantity = item.getQuantity();
        dto.productId = item.getProductId();
        dto.variantId = item.getVariantId();
        dto.productName = productName;
        dto.image = image;
        dto.price = price;
        return dto;
    }
}
