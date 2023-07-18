package com.example.sportstore;

import com.example.sportstore.dto.ProductDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class ShoppingCart {
    private List<ProductDto> products;
    private BigDecimal totalCost;

    public void addToCart(ProductDto productDto) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(productDto);
        if (totalCost == null) {
            totalCost = new BigDecimal(0);
        }
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(productDto.getPrice()));
        totalCost = totalCost.add(price);
    }

    public int getCartSize() {
        if (CollectionUtils.isEmpty(products)) {
            return 0;
        } else {
            return products.size();
        }
    }

    public void clearShoppingCart() {
        totalCost = new BigDecimal(0);
        products.clear();
    }
}
