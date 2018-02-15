package de.bringmeister.controller;

import java.util.Arrays;
import java.util.List;

import de.bringmeister.dao.price.IPriceDao;
import de.bringmeister.dao.product.IProductDao;
import de.bringmeister.domain.*;
import de.bringmeister.exceptions.PriceNotFoundException;
import de.bringmeister.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by petermihal2 on 08/02/2018.
 */
@RestController
@RequestMapping("/products")
public class ProductRestController {

  private final IPriceDao priceDao;

  private final IProductDao productDao;

  @Autowired
  public ProductRestController(IPriceDao priceDao, IProductDao productDao) {
    this.priceDao = priceDao;
    this.productDao = productDao;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public List<Product> findAll() {

    return productDao.getProducts();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ProductDetail findOne(@PathVariable("id") String id) {

    Product product = productDao.findOne(id);

    if(product == null) {
      throw new ProductNotFoundException(id);
    }

    List<ProductPrice> productPrices = priceDao.getPrices(product.getSku());

    return new ProductDetail(product, productPrices);
  }


    @RequestMapping(value = "/{id}/prices", method = RequestMethod.GET)
    @ResponseBody
    public List<Price> getProductPriceByUnit(@PathVariable("id") String id, @RequestParam("unit") String unitReq) {

        Product product = productDao.findOne(id);

        if(product == null) {
            throw new ProductNotFoundException(id);
        }

        // throw exception if theres no price for this unit
        Unit unit = Arrays.stream(Unit.values())
                    .filter(e -> e.name().equalsIgnoreCase(unitReq))
                    .findAny().orElseThrow(() -> new PriceNotFoundException(id, unitReq));

        return priceDao.getPrices(product.getSku(), unit);

    }

}












