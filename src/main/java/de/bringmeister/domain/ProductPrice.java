package de.bringmeister.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by petermihal2 on 11/02/2018.
 */
public class ProductPrice {

    private String productSku;
    private Unit unit;
    private Price price;

    @JsonIgnore
    public String getProductSku() {
        return productSku;
    }

    @JsonProperty("id")
    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "productSku='" + productSku + '\'' +
                ", unit=" + unit +
                ", price=" + price +
                '}';
    }
}

