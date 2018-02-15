package de.bringmeister.domain;

import org.springframework.beans.BeanUtils;

import java.util.List;

public class ProductDetail extends Product {

    private List<ProductPrice> productPrices;

    public ProductDetail(Product product, List<ProductPrice> productPrices){
        BeanUtils.copyProperties(product, this);
        this.productPrices = productPrices;
    }

    public List<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }
}
