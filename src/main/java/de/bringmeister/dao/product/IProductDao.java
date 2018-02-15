package de.bringmeister.dao.product;

import de.bringmeister.domain.Product;

import java.util.List;

/**
 * Created by petermihal2 on 11/02/2018.
 */
public interface IProductDao {

    List<Product> getProducts();

    Product findOne(String id);
}
