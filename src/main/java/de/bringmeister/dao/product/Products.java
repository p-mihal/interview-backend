package de.bringmeister.dao.product;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import de.bringmeister.domain.Product;

import java.util.List;

/**
 * Created by petermihal2 on 11/02/2018.
 */
@JacksonXmlRootElement(localName = "Products")
public class Products {

    @JacksonXmlElementWrapper(localName = "Product", useWrapping = false)
    @JacksonXmlProperty(localName="Product")
    private List<Product> products;

    public Products() {
    }

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Products{");
        sb.append("products=").append(products);
        sb.append('}');
        return sb.toString();
    }
}